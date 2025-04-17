package com.app.dto;

import java.util.ArrayList;
import java.util.List;

import com.app.pojos.Course;
import com.app.pojos.Student;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CourseWithStudentsResponseDto {
	
	private String courseName;
	private List<StudentRequestResponseDto> students = new ArrayList<>();
	
	public CourseWithStudentsResponseDto(Course course) {
		this.courseName = course.getTitle();
		for(Student stud : course.getStudents()) {
			students.add(new StudentRequestResponseDto(stud.getRollNumber(),stud.getFirstName(),stud.getSurname()));
		}
	}

}
