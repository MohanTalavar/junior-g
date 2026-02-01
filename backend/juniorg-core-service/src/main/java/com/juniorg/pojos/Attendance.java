package com.juniorg.pojos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.juniorg.enums.AttendanceStatus;

@Entity
@Table(
        name = "attendance_tbl",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_attendance_student_date",
                columnNames = { "student_id", "attendance_date" }
        ),
        indexes = {
                @Index(name = "idx_attendance_date", columnList = "attendance_date"),
                @Index(name = "idx_attendance_student", columnList = "student_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
// avoid including student (LAZY) in toString to prevent accidental load
@ToString(exclude = "student")
public class Attendance extends BaseEntity {

    @Column(name = "attendance_date", nullable = false)
    @NotNull
    private LocalDate attendanceDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    @NotNull
    private AttendanceStatus status;

    @Column(length = 255)
    @Size(max = 255)
    private String remarks;

    @Column(name = "marked_by", length = 100)
    @Size(max = 100)
    private String markedBy;

    /**
     * If BaseEntity already contains createdAt, remove this field and rely on BaseEntity.
     * Otherwise this provides an audit timestamp for when attendance was recorded.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Many attendance entries -> One student
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false, foreignKey = @ForeignKey(name = "fk_attendance_student"))
    private Student student;

    // Convenience constructor for service-layer creation
    public Attendance(Student student, LocalDate attendanceDate, AttendanceStatus status, String markedBy, String remarks) {
        this.student = student;
        this.attendanceDate = attendanceDate;
        this.status = status;
        this.markedBy = markedBy;
        this.remarks = remarks;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
