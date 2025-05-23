package com.app.service;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.pojos.Address;
import com.app.pojos.Student;
import com.app.pojos.Teacher;
import com.app.repo.AddressRepo;
import com.app.repo.StudentRepo;
import com.app.repo.TeacherRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class AddressServiceImpl implements IAddressService {

	private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private TeacherRepo teacherRepo;

	@Override
	public String addOrUpdateStudentAddress(Long studentId, Address newAddress) {

		log.info("Processing address for studentId: {}. New address: {}", studentId, newAddress);
		String msg = "";

		Student persistenStudent = studentRepo.findById((long) studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));

		Address existingAddress = persistenStudent.getAddress();
		if (existingAddress != null) {
			existingAddress.setCity(newAddress.getCity());
			existingAddress.setState(newAddress.getState());
			existingAddress.setCountry(newAddress.getCountry());
			existingAddress.setZipCode(newAddress.getZipCode());
			msg = "Address detailed updated for " + persistenStudent.getFirstName();
		} else {
			persistenStudent.setAddress(newAddress);
		}

		msg = "Address detailed saved for " + persistenStudent.getFirstName();
		return msg;
	}

	@Override
	public String addOrUpdateTeacherAddress(Long teacherId, Address newAddress) {

		log.info("Processing address for teacherId: {}. New address: {}", teacherId, newAddress);
		String msg = "";

		Teacher persistentTeacher = teacherRepo.findById(teacherId)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher not found " + teacherId));

		Address existingAddress = persistentTeacher.getAddress();
		if (existingAddress != null) {
			existingAddress.setCity(newAddress.getCity());
			existingAddress.setState(newAddress.getState());
			existingAddress.setCountry(newAddress.getCountry());
			existingAddress.setZipCode(newAddress.getZipCode());
			msg = "Address detailed updated for " + persistentTeacher.getFirstName();
		} else {
			persistentTeacher.setAddress(newAddress);
		}

		msg = "Address detailed saved for " + persistentTeacher.getFirstName();
		return msg;
	}

}
