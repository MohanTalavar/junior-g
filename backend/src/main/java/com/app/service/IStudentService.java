package com.app.service;

import com.app.pojos.Student;

public interface IStudentService {

	// add method to add new student
	Student admitNewStudent(String courseName, Student stud);
	
	// add method to cancel student admission
	String cancelStudentAdmission(String courseName, Long studId);
	
	// add method to fetch student details
	Student getStudentDetails(Long StudId);
	
	// add method to update student details
	Student updateStudentRecord(Long studId, Student updatedStudent);
}
