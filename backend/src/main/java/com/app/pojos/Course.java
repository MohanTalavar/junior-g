package com.app.pojos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.app.dto.CourseRequestResponseDto;
import com.app.dto.CourseWithStudentsRequestDto;
import com.app.dto.StudentRequestResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "courses_tbl")
//Course : one side of the asso, parent side , inverse side
public class Course extends BaseEntity {

	@Column(length = 30, unique = true)
	private String title;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	private double fees;

	private int capacity;
	// if you want to establish one --> many association(HAS-A) b/w course n student
	// do u need to add any additional property ? YES
	// Here we are looking form one to many bi-directional association
	// Course 1 ---> * Student
	// mappedBy to let hibernate know which is the owning side and will
	// Not create the extra table
	// mappedBy is only used in Bi-Directional asso
	// mappedBy is used on the inverse side to tell hibernate which is owning side
	// i.e student
	@OneToMany(mappedBy = "choosenCourse", cascade = CascadeType.ALL, orphanRemoval = true /*
																							 * ,fetch = FetchType.EAGER
																							 */)
	private List<Student> students = new ArrayList<>(); // ALWAYS init the collection to empty collection --by founder
														// of hibernate

	/*
	 * cascades all (save,update,delete) operations on parent side --> child
	 */
	/*
	 * Orphan removal = true will delete the delinked students Currently we looked
	 * that hibernated fired update query and made student record FK -> null instead
	 * of deleting the record to solve this we add this attribute and toggle the
	 * orphan removal flag to true Now hibernate will fire the delete query to
	 * remove the orphan students/ associated delinked records
	 */

	/*
	 * fetch = FetchType.EAGER ||Solution 1 for LazyInitializeException || NOT
	 * Recommended for any to many asso This is the sol for
	 * LazyInitializationException, now whenever we fetch course ref now it will
	 * aslo load the associated students records to it. This is one of the soln's
	 * for LazyInitializationException but NOT recommended we have other soln's as
	 * well
	 */

	@ManyToMany(mappedBy = "courses") // inverse side of relationShip
	private List<Teacher> teachers = new ArrayList<>();

	public Course(String title, LocalDate startDate, LocalDate endDate, double fees, int capacity) {
		super();
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.fees = fees;
		this.capacity = capacity;
	}

	public Course(CourseRequestResponseDto dto) {
		this.title = dto.getTitle();
		this.startDate = dto.getStartDate();
		this.endDate = dto.getEndDate();
		this.fees = dto.getFees();
		this.capacity = dto.getCapacity();
	}

	public Course(CourseWithStudentsRequestDto dto) {
		this.title = dto.getTitle();
		this.startDate = dto.getStartDate();
		this.endDate = dto.getEndDate();
		this.fees = dto.getFees();
		this.capacity = dto.getCapacity();

		for (StudentRequestResponseDto stud : dto.getStudents()) {
			this.addStudent(new Student(stud)); // establishing bi-dir linking
		}

	}

	// Suggestion form the founder: add helper methods : for establishing the link
	// is it mandatory : NO Optional BUT recommended

	public void addStudent(Student newStud) { // establishing the link

		// add student ref in course
		students.add(newStud);// course --> stud

		// assign course ref to the student
		newStud.setChoosenCourse(this);

	}

	public void removeStudent(Student stud) { // delink

		// remove student ref in course
		students.remove(stud);// course --> stud

		// remove course ref from the student
		stud.setChoosenCourse(null);

	}

	// NEVER ADD association fields(eg: students) in toString
	@Override
	public String toString() {
		return "Course [title=" + title + ", startDate=" + startDate + ", endDate=" + endDate + ", fees=" + fees
				+ ", capacity=" + capacity + ", getId()=" + getId() + "]";
	}

}
