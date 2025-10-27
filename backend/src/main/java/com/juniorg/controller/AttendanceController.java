package com.juniorg.controller;

import com.juniorg.dto.AttendanceDto;
import com.juniorg.dto.AttendanceResponseDto;
import com.juniorg.service.IAttendanceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final IAttendanceService attendanceService;
    private static final Logger log = LoggerFactory.getLogger(AttendanceController.class);

    /**
     * Mark attendance for a single student.
     */
    @PostMapping("/mark")
    public ResponseEntity<AttendanceResponseDto> markAttendance(@RequestBody AttendanceDto dto) {

        log.info("Received request to mark attendance for student ID: {} on date: {}",
                dto.getStudentId(), dto.getAttendanceDate());
        AttendanceResponseDto saved = attendanceService.markAttendance(dto) ;
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Mark attendance in bulk (for a whole class/day).
     */
    @PostMapping("/mark/bulk")
    public ResponseEntity<List<AttendanceResponseDto>> markAttendanceBulk(@RequestBody List<AttendanceDto> attendanceList) {
        log.info("Received bulk attendance marking request for {} students", attendanceList.size());
        List<AttendanceResponseDto> saved = attendanceService.markAttendanceBulk(attendanceList);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Get all attendance records for a single student.
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceResponseDto>> getByStudent(@PathVariable Long studentId) {
        log.info("Fetching attendance for student ID: {}", studentId);
        List<AttendanceResponseDto> list = attendanceService.getAttendanceByStudent(studentId);
        return ResponseEntity.ok(list);
    }

    /**
     * Get attendance of all students on a specific date. format : yyyy-MM-dd
     */
    @GetMapping("/date/{date}")
    public ResponseEntity<List<AttendanceResponseDto>> getByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Fetching attendance for date: {}", date);
        List<AttendanceResponseDto> list = attendanceService.getAttendanceByDate(date);
        return ResponseEntity.ok(list);
    }

    /**
     * Get attendance between two dates (paginated).
     * GET http://localhost:8080/attendances/range?startDate={startDate}&endDate={endDate}&page={page}&size={size}
     */
    @GetMapping("/range")
    public ResponseEntity<Page<AttendanceResponseDto>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Fetching paginated attendance records from {} to {}, page: {}, size: {}", startDate, endDate, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<AttendanceResponseDto> result = attendanceService.getAttendanceByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(result);
    }

    /**
     * Update an existing attendance record.
     */
    @PutMapping("/{attendanceId}")
    public ResponseEntity<AttendanceResponseDto> updateAttendance(@PathVariable Long attendanceId,
                                                       @RequestBody AttendanceDto dto) {
        log.info("Updating attendance record with ID: {}", attendanceId);
        AttendanceResponseDto updated = attendanceService.updateAttendance(attendanceId, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete an attendance record.
     */
    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long attendanceId) {
        log.warn("Deleting attendance record with ID: {}", attendanceId);
        attendanceService.deleteAttendance(attendanceId);
        return ResponseEntity.noContent().build();
    }
}
