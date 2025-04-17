package com.app.pojos;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.app.dto.StudentRequestResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students_tbl")
@Getter
@Setter
@NoArgsConstructor
//Student : many side of the asso, child side, 
//owning side(will contain the FK--> Referencing to PK of the course table)
public class Student extends BaseEntity {

	@Column(length = 20)
	private String firstName;

	@Column(name = "middle_name", length = 20)
	private String middleName;

	@Column(length = 20)
	private String surname;

	@Column(length = 30, unique = true)
	private String email;

	@Column(name = "roll_number", length = 3, unique = true)
	private String rollNumber;

	@Column(length = 10)
	private String phoneNumber;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Column(length = 10)
	private String gender;

	@Column(name = "father_name", length = 30)
	private String fatherName;

	@Column(name = "mother_name", length = 30)
	private String motherName;

	@Column(name = "emergency_contact", length = 10)
	private String emergencyContact;

	@Column(name = "admission_date")
	private LocalDate admissionDate;

	@Column(name = "blood_group", length = 10)
	private String bloodGroup;

	// what should be the additional prop for mapping a bi -dir asso,
	// so that one can find out chosen course's details from stud?
	// Course 1<---*Student
	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course choosenCourse;
	// else it takes col name "choosen_course_id"
	// nullable = flase :: NOT NULL Constraint for the FK

	// uni dir OneToOne mapping from student to address
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "address_id")
	private Address address;

	public Student(StudentRequestResponseDto stud) {
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

	@Override
	public String toString() {
		return "Student [firstName=" + firstName + ", middleName=" + middleName + ", surname=" + surname + ", email="
				+ email + ", rollNumber=" + rollNumber + ", phoneNumber=" + phoneNumber + ", dateOfBirth=" + dateOfBirth
				+ ", gender=" + gender + ", fatherName=" + fatherName + ", motherName=" + motherName
				+ ", emergencyContact=" + emergencyContact + ", admissionDate=" + admissionDate + ", bloodGroup="
				+ bloodGroup + "]";
	}

}
