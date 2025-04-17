package com.app.service;

import com.app.pojos.Teacher;

public interface ITeacherService {
	
	// add a method to store new teacher details
	String addNewTeacher(String courseName, Teacher newTeacher);
	
	// add a method to get the teacher details
	Teacher retrieveTeacherDetails(Long teacherId);
	
	// add a method to delete the teacher details
	String deleteTeacherRecord(Long teacherId);
	
	// add a method to update the teacher details
	Teacher updateTeacherRecord(Long teacherId, Teacher updatedTeacher);

}
