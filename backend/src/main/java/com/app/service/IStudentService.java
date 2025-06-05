package com.app.service;

import com.app.pojos.Student;

public interface IStudentService {

	// add method to add new student
	Student admitNewStudent(String courseName, Student stud);
	
	// add method to cancel student admission
	String cancelStudentAdmission(String courseName, String studRollNo);
	
	// add method to fetch student details
	Student getStudentDetails(String studentRollNo);
	
	// add method to update student details
	Student updateStudentRecord(Long studId, Student updatedStudent);

	// add method to update student details by roll no
	Student updateStudentRecordByRollNo(String rollNumber,Student updatedStudent);
}
