package com.juniorg.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juniorg.pojos.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    /**
     * Find attendance record for a specific student on a specific date.
     * Used to prevent duplicate entries.
     */
    Optional<Attendance> findByStudentIdAndAttendanceDate(Long studentId, LocalDate attendanceDate);

    /**
     * Get all attendance records for a student.
     * Useful for showing attendance history.
     */
    List<Attendance> findByStudentId(Long studentId);

    /**
     * Get attendance for all students on a particular date.
     * Used for daily attendance overview.
     */
    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    /**
     * Paginated version of date range query.
     * Used when large result sets need to be shown in pages.
     */
    Page<Attendance> findByAttendanceDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

}