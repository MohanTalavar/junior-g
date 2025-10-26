package com.juniorg.service;

import com.juniorg.custom_exception.ResourceNotFoundException;
import com.juniorg.pojos.Address;
import com.juniorg.pojos.Student;
import com.juniorg.pojos.Teacher;
import com.juniorg.repo.StudentRepo;
import com.juniorg.repo.TeacherRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {

	private final StudentRepo studentRepo;
	private final TeacherRepo teacherRepo;
	private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

	@Override
	public String addOrUpdateStudentAddress(Long studentId, Address newAddress) {

		log.info("Processing address for studentId: {}. New address: {}", studentId, newAddress);

		Student persistenStudent = studentRepo.findById((long) studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));

		updateOrSetAddress(persistenStudent.getAddress(), newAddress, persistenStudent::setAddress);

		return "Address details saved for " + persistenStudent.getFirstName();
	}

	@Override
	public String addOrUpdateTeacherAddress(Long teacherId, Address newAddress) {

		log.info("Processing address for teacherId: {}. New address: {}", teacherId, newAddress);

		Teacher persistentTeacher = teacherRepo.findById(teacherId)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher not found " + teacherId));

		updateOrSetAddress(persistentTeacher.getAddress(), newAddress, persistentTeacher::setAddress);

		return "Address details saved for " + persistentTeacher.getFirstName();
	}

	/**
	 * Helper method to either update an existing address or set a new one.
	 */
	private void updateOrSetAddress(Address existing, Address updated, java.util.function.Consumer<Address> setter) {
		if (existing != null) {
			existing.setCity(updated.getCity());
			existing.setState(updated.getState());
			existing.setCountry(updated.getCountry());
			existing.setZipCode(updated.getZipCode());
		} else {
			setter.accept(updated);
		}
	}

}
