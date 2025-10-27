package com.juniorg.service;

import java.time.LocalDate;
import java.util.List;

import com.juniorg.dto.AttendanceResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.juniorg.dto.AttendanceDto;
import com.juniorg.pojos.Attendance;

/**
 * Service layer interface for managing student attendance.
 * Defines all business operations for attendance marking, retrieval, and updates.
 */
public interface IAttendanceService {

    /**
     * Marks attendance for a single student on a given date.
     * Throws exception if attendance already exists for that student and date.
     */
    AttendanceResponseDto markAttendance(AttendanceDto dto);

    /**
     * Marks attendance for multiple students at once (bulk marking).
     * Typically used when a teacher submits a full day's attendance.
     */
    List<AttendanceResponseDto> markAttendanceBulk(List<AttendanceDto> attendanceList);

    /**
     * Retrieves all attendance records for a specific student.
     * Used for student detail or parent report view.
     */
    List<AttendanceResponseDto> getAttendanceByStudent(Long studentId);

    /**
     * Retrieves attendance records for all students on a specific date.
     * Useful for teacher’s daily attendance overview.
     */
    List<AttendanceResponseDto> getAttendanceByDate(LocalDate date);

    /**
     * Retrieves attendance records between two dates (paginated).
     * Used for dashboards with pagination or admin panels.
     */
    Page<AttendanceResponseDto> getAttendanceByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);

    /**
     * Updates the attendance record (for correction or status change).
     * Only status, remarks, and markedBy should be editable — not date/student.
     */
    AttendanceResponseDto updateAttendance(Long attendanceId, AttendanceDto dto);

    /**
     * Deletes an attendance record by ID (if correction needed).
     * Generally used only by admin-level users.
     */
    void deleteAttendance(Long attendanceId);
}
