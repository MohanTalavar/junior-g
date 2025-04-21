package com.app.service;

import com.app.dto.CourseRequestResponseDto;
import com.app.pojos.Course;

import java.util.ArrayList;
import java.util.List;

public interface ICourseService {
	// add new method to insert new course details
	String launchNewCourse(Course transienCourse);

	// add a method to get the course list
	List<Course> fetchCourses();
	
	// add new method to delete existing course details
	String removeCourse(String transienCourse);
	
	// add a method to get the course details
	Course getCourseDetails(String title);
	
	// add a method to get the course and student details : using students size() as a hint to hib
	Course getCourseAndStudentDetails(String title);
	
	// add a method to get the course and student details with help of join fetch
	Course getCourseAndStudentDetailsJoinFetch(String title);
	

}
