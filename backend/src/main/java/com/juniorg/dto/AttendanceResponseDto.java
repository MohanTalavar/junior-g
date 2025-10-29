package com.juniorg.dto;

import java.time.LocalDate;
import com.juniorg.enums.AttendanceStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceResponseDto {
    private Long id;
    private Long studentId;
    private String rollNumber;
    private String studentName;
    private LocalDate attendanceDate;
    private AttendanceStatus status;
    private String markedBy;
    private String remarks;
}
