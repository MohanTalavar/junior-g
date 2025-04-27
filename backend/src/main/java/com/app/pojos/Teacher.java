package com.app.pojos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PastOrPresent;

import com.app.dto.TeacherRequestResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teachers_tbl")
public class Teacher extends BaseEntity {

	@Column(name ="first_name", length = 20)
	private String firstName;
	
	@Column(name ="last_name", length = 20)
	private String lastName;
	
	@Column(name ="email", length = 30, unique = true)
	private String email;
	
	@Column(name ="phone_number",length = 15, unique = true)
	private String phoneNumber;
	
	@Column(length = 30)
	private String qualification;
	
	@PastOrPresent
	private LocalDate dateOfJoining;

	// we need to establish Teacher * <---> Course relationship
	// Lets make the teacher as the owning side
	@ManyToMany
	@JoinTable(name = "teacher_course_tbl", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> courses = new ArrayList<>();
	
	// uni dir  OneToOne mapping with address
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "address_id")
	private Address address;
	
	public Teacher(TeacherRequestResponseDto teacherDto) {
		this.firstName = teacherDto.getFirstName();
		this.lastName = teacherDto.getLastName();
		this.email = teacherDto.getEmail();
		this.phoneNumber = teacherDto.getPhoneNumber();
		this.qualification = teacherDto.getQualification();
		this.dateOfJoining = teacherDto.getDateOfJoining();
	}

	// Add the Helper methods

	public void addCourse(Course course) {
		courses.add(course);
		course.getTeachers().add(this);
	}

	public void removeCourse(Course course) {
		courses.remove(course);
		course.getTeachers().remove(this);
	}

	@Override
	public String toString() {
		return "Teacher [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phoneNumber="
				+ phoneNumber + ", qualification=" + qualification + ", dateOfJoining=" + dateOfJoining + "]";
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

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
