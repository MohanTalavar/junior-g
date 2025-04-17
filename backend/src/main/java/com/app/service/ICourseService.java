package com.app.service;

import com.app.pojos.Course;

public interface ICourseService {
	// add new method to insert new course details
	String launchNewCourse(Course transienCourse);
	
	// add new method to delete existing course details
	String removeCourse(String transienCourse);
	
	// add a method to get the course details
	Course getCourseDetails(String title);
	
	// add a method to get the course and student details : using students size() as a hint to hib
	Course getCourseAndStudentDetails(String title);
	
	// add a method to get the course and student details with help of join fetch
	Course getCourseAndStudentDetailsJoinFetch(String title);
	

}
