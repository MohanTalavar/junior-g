package com.juniorg.enums;

/**
 * Enum representing possible attendance states for a student.
 * Stored in DB as STRING (see Attendance.java @Enumerated).
 */
public enum AttendanceStatus {
    PRESENT,   // Student attended school
    ABSENT,    // Student did not attend
    LEAVE;     // Student was on approved leave
}