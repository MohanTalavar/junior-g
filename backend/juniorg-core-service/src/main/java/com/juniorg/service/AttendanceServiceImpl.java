package com.juniorg.service;

import com.juniorg.custom_exception.ResourceNotFoundException;
import com.juniorg.dto.AttendanceDto;
import com.juniorg.dto.AttendanceResponseDto;
import com.juniorg.enums.AttendanceStatus;
import com.juniorg.pojos.Attendance;
import com.juniorg.pojos.Student;
import com.juniorg.repo.AttendanceRepository;
import com.juniorg.repo.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceServiceImpl implements IAttendanceService {

    private final AttendanceRepository attendanceRepo;
    private final StudentRepo studentRepo;
    private final ModelMapper modelMapper;
    private static final Logger log = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    /**
     * Marks attendance for a single student.
     * Prevents duplicate attendance entries for the same date.
     */
    @Override
    public AttendanceResponseDto markAttendance(AttendanceDto dto) {

        log.info("Marking attendance for student ID: {} on date: {}", dto.getStudentId(), dto.getAttendanceDate());

        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() ->{
                    log.error("Student not found with ID: {}", dto.getStudentId());
                    return new ResourceNotFoundException("Student not found with ID: " + dto.getStudentId());
                });

        // Check if attendance already exists for this student on this date
        attendanceRepo.findByStudentIdAndAttendanceDate(student.getId(), dto.getAttendanceDate())
                .ifPresent(a -> {
                    log.warn("Duplicate attendance entry detected for student ID: {} on date: {}", student.getId(), dto.getAttendanceDate());
                    throw new RuntimeException("Attendance already marked for this student and date");
                });

        Attendance attendance = new Attendance(
                student,
                dto.getAttendanceDate(),
                AttendanceStatus.valueOf(dto.getStatus()),
                dto.getMarkedBy(),
                dto.getRemarks()
        );

        try {
            Attendance saved = attendanceRepo.save(attendance);
            log.info("Attendance marked successfully for student ID: {} on date: {}", student.getId(), dto.getAttendanceDate());
            return convertToDto(saved);
        } catch (DataIntegrityViolationException e) {
            log.error("Duplicate key violation while marking attendance for student ID: {}", student.getId(), e);
            throw new RuntimeException("Duplicate attendance entry detected", e);
        }
    }

    /**
     * Marks attendance for multiple students (bulk insert).
     * Skips duplicates but continues marking the rest.
     */
    @Override
    public List<AttendanceResponseDto> markAttendanceBulk(List<AttendanceDto> attendanceList) {

        log.info("Starting bulk attendance marking for {} students", attendanceList.size());

        List<AttendanceResponseDto> savedRecords = new ArrayList<>();

        for (AttendanceDto dto : attendanceList) {
            try {
                AttendanceResponseDto saved = markAttendance(dto);
                savedRecords.add(saved);
            } catch (RuntimeException ex) {
                log.warn("Skipping duplicate or invalid attendance entry for student ID: {} on date: {}",
                        dto.getStudentId(), dto.getAttendanceDate());
            }
        }

        log.info("Bulk attendance marking completed. Successfully saved {} of {} records.",
                savedRecords.size(), attendanceList.size());

        return savedRecords;
    }

    /**
     * Fetch all attendance records for a student.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> getAttendanceByStudent(Long studentId) {

        log.info("Fetching attendance for student ID: {}", studentId);

        List<Attendance> list = attendanceRepo.findByStudentId(studentId);

        if (list.isEmpty()) {
            log.warn("No attendance records found for student ID: {}", studentId);
        } else {
            log.info("Found {} attendance records for student ID: {}", list.size(), studentId);
        }

        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Fetch attendance for all students on a specific date.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> getAttendanceByDate(LocalDate date) {

        log.info("Fetching attendance for date: {}", date);

        List<Attendance> list = attendanceRepo.findByAttendanceDate(date);

        if (list.isEmpty()) {
            log.warn("No attendance records found for date: {}", date);
        } else {
            log.info("Found {} attendance records for date: {}", list.size(), date);
        }

        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Fetch attendance between two dates (paginated).
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceResponseDto> getAttendanceByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {

        log.info("Fetching paginated attendance from {} to {} (page: {}, size: {})",
                startDate, endDate, pageable.getPageNumber(), pageable.getPageSize());

        Page<Attendance> result = attendanceRepo.findByAttendanceDateBetween(startDate, endDate, pageable);

        if (result.isEmpty()) {
            log.warn("No attendance records found between {} and {}", startDate, endDate);
        } else {
            log.info("Fetched {} attendance records between {} and {}", result.getContent().size(), startDate, endDate);
        }

        return result.map(this::convertToDto);
    }

    /**
     * Update an existing attendance record (status/remarks/markedBy only).
     */
    @Override
    public AttendanceResponseDto updateAttendance(Long attendanceId, AttendanceDto dto) {

        log.info("Updating attendance ID: {}", attendanceId);

        Attendance attendance = attendanceRepo.findById(attendanceId)
                .orElseThrow(() -> {
                    log.error("Attendance record not found with ID: {}", attendanceId);
                    return new ResourceNotFoundException("Attendance not found with ID: " + attendanceId);
                });

        attendance.setStatus(AttendanceStatus.valueOf(dto.getStatus()));
        attendance.setRemarks(dto.getRemarks());
        attendance.setMarkedBy(dto.getMarkedBy());

        Attendance updated = attendanceRepo.save(attendance);
        log.info("Updated attendance ID: {} successfully", attendanceId);

        return convertToDto(updated);
    }

    /**
     * Deletes an attendance record by ID.
     */
    @Override
    public void deleteAttendance(Long attendanceId) {

        log.info("Deleting attendance record with ID: {}", attendanceId);

        if (!attendanceRepo.existsById(attendanceId)) {
            log.error("Attempted to delete non-existing attendance record with ID: {}", attendanceId);
            throw new ResourceNotFoundException("Attendance record not found with ID: " + attendanceId);
        }

        attendanceRepo.deleteById(attendanceId);
        log.info("Deleted attendance record successfully with ID: {}", attendanceId);
    }

    private AttendanceResponseDto convertToDto(Attendance attendance) {
        AttendanceResponseDto dto = new AttendanceResponseDto();
        dto.setId(attendance.getId());
        dto.setRollNumber(attendance.getStudent().getRollNumber());
        dto.setStudentId(attendance.getStudent().getId());
        dto.setStudentName(attendance.getStudent().getFirstName() + " " + attendance.getStudent().getSurname());
        dto.setAttendanceDate(attendance.getAttendanceDate());
        dto.setStatus(attendance.getStatus());
        dto.setMarkedBy(attendance.getMarkedBy());
        dto.setRemarks(attendance.getRemarks());
        return dto;
    }

    public Page<AttendanceResponseDto> getAttendanceByStudentRollNumber(String rollNumber, Pageable pageable) {
        // 1️⃣ Find the student by roll number
        Student student = studentRepo.findByRollNumber(rollNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with roll number: " + rollNumber));

        // 2️⃣ Fetch paginated attendance records for that student
        Page<Attendance> attendancePage = attendanceRepo.findByStudent(student, pageable);

        // 3️⃣ Map each Attendance -> AttendanceResponseDto manually
        return attendancePage.map(att -> {
            AttendanceResponseDto dto = new AttendanceResponseDto();
            dto.setId(att.getId());
            dto.setStudentId(student.getId());
            dto.setRollNumber(student.getRollNumber());
            dto.setStudentName(student.getFirstName() + " " + student.getSurname());
            dto.setAttendanceDate(att.getAttendanceDate());
            dto.setStatus(att.getStatus());
            dto.setMarkedBy(att.getMarkedBy());
            dto.setRemarks(att.getRemarks());
            return dto;
        });
    }

}
