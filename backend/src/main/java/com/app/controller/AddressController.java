package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AddressRequestResponseDto;
import com.app.pojos.Address;
import com.app.service.IAddressService;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private IAddressService addressService;

	@PostMapping("/store-student-address/{studId}")
	public ResponseEntity<String> storeStudentsAddress(@PathVariable Long studId,
			@RequestBody AddressRequestResponseDto transientAddress) {

		String response = addressService.addOrUpdateStudentAddress(studId, new Address(transientAddress));

		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}

	@PutMapping("/update-student-address/{studId}")
	public ResponseEntity<String> updateStudentAddress(@PathVariable Long studId, @RequestBody AddressRequestResponseDto updatedAddress) {

		String response = addressService.addOrUpdateStudentAddress(studId, new Address(updatedAddress));

		return ResponseEntity.ok(response);

	}
	
	@PostMapping("/store-teacher-address/{teacherId}")
	public ResponseEntity<String> storeTeacherAddress(@PathVariable Long teacherId,
			@RequestBody AddressRequestResponseDto transientAddress) {

		String response = addressService.adddOrUpdateTeacherAddress(teacherId, new Address(transientAddress));

		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}
	
	@PutMapping("/update-teacher-address/{teacherId}")
	public ResponseEntity<String> updateTeacherAddress(@PathVariable Long teacherId, @RequestBody AddressRequestResponseDto updatedAddress) {

		String response = addressService.adddOrUpdateTeacherAddress(teacherId, new Address(updatedAddress));

		return ResponseEntity.ok(response);

	}
	

}
