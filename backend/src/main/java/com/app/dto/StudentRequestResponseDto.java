package com.app.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.app.pojos.Student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestResponseDto {

	@NotBlank(message = "First name cannot be blank")
	@Size(max = 20, message = "First name must not exceed 20 characters")
	private String firstName;
	
	@Size(max = 20, message = "Middle name must not exceed 20 characters")
    private String middleName;
	
	@NotBlank(message = "Surname cannot be blank")
    @Size(max = 20, message = "Surname must not exceed 20 characters")
	private String surname;
	
	@Email(message = "Invalid email address format")
	private String email;
	
	@NotBlank(message = "Roll number cannot be blank")
    @Size(max = 3, message = "Roll number must not exceed 3 characters")
    private String rollNumber;
	
	@Pattern(regexp = "\\d{10}", message = "Phone number must be a valid 10-digit number")
    private String phoneNumber;
	
	@NotNull(message = "Date of birth cannot be null")
	@Past(message = "Date of birth must be a past date")
	private LocalDate dateOfBirth;
	 
	@NotBlank(message = "Gender cannot be blank")
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be either 'Male', 'Female', or 'Other'")
    private String gender;
	
	@NotBlank(message = "Father's name cannot be blank")
    @Size(max = 30, message = "Father's name must not exceed 30 characters")
	private String fatherName;
	
	@NotBlank(message = "Mother's name cannot be blank")
    @Size(max = 30, message = "Mother's name must not exceed 30 characters")
	private String motherName;
	
	@Pattern(regexp = "\\d{10}", message = "Emergency contact must be a valid 10-digit number")    
	private String emergencyContact;
	
	@NotNull(message = "Admission date cannot be null")
	@PastOrPresent(message = "Admission date must be a past or present date")
	private LocalDate admissionDate;
	
	@NotBlank(message = "Blood group cannot be blank")
    @Pattern(regexp = "(A|B|AB|O)[+-]", message = "Blood group must be in the format 'A+', 'B-', etc.")    
	private String bloodGroup;

	public StudentRequestResponseDto(Student stud) {

		this.firstName = stud.getFirstName();
		this.middleName = stud.getMiddleName();
		this.surname = stud.getSurname();
		this.email = stud.getEmail();
		this.rollNumber = stud.getRollNumber();
		this.phoneNumber = stud.getPhoneNumber();
		this.dateOfBirth = stud.getDateOfBirth();
		this.gender = stud.getGender();
		this.fatherName = stud.getFatherName();
		this.motherName = stud.getMotherName();
		this.emergencyContact = stud.getEmergencyContact();
		this.admissionDate = stud.getAdmissionDate();
		this.bloodGroup = stud.getBloodGroup();

	}
	
	public StudentRequestResponseDto(String rollNumber,String firstName, String surname) {
		this.rollNumber = rollNumber;
		this.firstName = firstName;
		this.surname = surname;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public LocalDate getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(LocalDate admissionDate) {
		this.admissionDate = admissionDate;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
}
