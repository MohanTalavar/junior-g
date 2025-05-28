package com.app.dto;

import com.app.pojos.Course;
import com.app.pojos.Teacher;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeacherRequestResponseDto {

    private Long id;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 20, message = "First name must not exceed 20 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 20, message = "Last name must not exceed 20 characters")
    private String lastName;

    @Email(message = "Invalid email address format")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be a valid 10-digit number")
    private String phoneNumber;

    @NotBlank(message = "Qualification cannot be blank")
    @Size(max = 30, message = "Qualification must not exceed 30 characters")
    private String qualification;

    @NotNull(message = "Date of joining cannot be null")
    @PastOrPresent
    private LocalDate dateOfJoining;

    public TeacherRequestResponseDto(Teacher teacher) {

        this.id = teacher.getId();
        this.firstName = teacher.getFirstName();
        this.lastName = teacher.getLastName();
        this.email = teacher.getEmail();
        this.phoneNumber = teacher.getPhoneNumber();
        this.qualification = teacher.getQualification();
        this.dateOfJoining = teacher.getDateOfJoining();
    }
}
