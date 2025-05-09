package com.app.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.app.pojos.Teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeacherRequestResponseDto {
	
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
		
		this.firstName = teacher.getFirstName();
		this.lastName = teacher.getLastName();
		this.email = teacher.getEmail();
		this.phoneNumber = teacher.getPhoneNumber();
		this.qualification = teacher.getQualification();
		this.dateOfJoining = teacher.getDateOfJoining();
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
}
