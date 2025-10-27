package com.juniorg.dto;

import java.util.ArrayList;
import java.util.List;

import com.juniorg.pojos.Course;

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
		this.students = course.getStudents().stream().map(StudentRequestResponseDto::new).toList();
	}

}
