package com.juniorg.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object for attendance operations.
 * Used for marking, updating, and viewing attendance.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {

    /**
     * ID of the student whose attendance is being marked.
     */
    private Long studentId;

    /**
     * Date for which attendance is being recorded.
     */
    private LocalDate attendanceDate;

    /**
     * Attendance status — must be one of PRESENT, ABSENT, or LEAVE.
     */
    private String status;

    /**
     * Name or ID of the person (usually teacher/admin) who marked attendance.
     */
    private String markedBy;

    /**
     * Optional remarks (e.g., "sick leave", "late arrival", etc.).
     */
    private String remarks;

}
