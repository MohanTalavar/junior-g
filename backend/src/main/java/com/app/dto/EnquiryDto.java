package com.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EnquiryDto {

    @NotBlank(message = "Student name cannot be blank")
    @Size(max = 40, message = "First name must not exceed 20 characters")
    private String studentName;

    @NotBlank(message = "Parent name cannot be blank")
    @Size(max = 40, message = "Parent name must not exceed 20 characters")
    private String parentName;

    @Email(message = "Invalid email address format")
    private String emailId;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be a valid 10-digit number")
    private String contactNo;

    private String courseName;
}
