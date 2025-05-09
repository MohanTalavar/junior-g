package com.app.service;

import com.app.pojos.Address;

public interface IAddressService {
	
	// add a method to set and update the address details of student
	String addOrUpdateStudentAddress(Long studentId, Address address);
	
	// add a method to set and update the address details of teacher
	String addOrUpdateTeacherAddress(Long teacherId, Address address);
	

}
