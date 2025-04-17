package com.app.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.StudentRequestResponseDto;
import com.app.pojos.Student;
import com.app.service.IStudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private IStudentService studService;

	@GetMapping("/get-student-details/{studentId}")
	public ResponseEntity<StudentRequestResponseDto> getStudent(@PathVariable Long studentId) {

		StudentRequestResponseDto student = new StudentRequestResponseDto(studService.getStudentDetails(studentId));
		return ResponseEntity.ok(student);
	}

	@PostMapping("/admit-new-student-to-course/{courseName}")
	public ResponseEntity<StudentRequestResponseDto> newStudentAdmission(@PathVariable String courseName,
			@Valid @RequestBody StudentRequestResponseDto transientStudent) {

		Student savedStudent = studService.admitNewStudent(courseName, new Student(transientStudent));
		StudentRequestResponseDto reponse = new StudentRequestResponseDto(savedStudent);
		return ResponseEntity.status(HttpStatus.CREATED).body(reponse);
	}

	@DeleteMapping("/student-admission-cancel/{courseName}/{studId}")
	public ResponseEntity<String> cancelStudentAdmission(@PathVariable String courseName, @PathVariable Long studId) {

		return ResponseEntity.ok(studService.cancelStudentAdmission(courseName, studId));
		// If the student record is not deleted and you receive th error
		// An unexpected error occured: could not execute statement; SQL [n/a];
		// constraint [null]; nested exception is
		// org.hibernate.exception.ConstraintViolationException: could not execute
		// statement
		// Reason because Student is acting as parent for the address tbl
		// Sol first remove the address record and then cancel the admission
	}

	@PutMapping("/update-student-details/{studId}")
	public ResponseEntity<StudentRequestResponseDto> updateStudentDetails(@PathVariable Long studId,
			@Valid @RequestBody StudentRequestResponseDto updatedStudent) {

		Student student = studService.updateStudentRecord(studId, new Student(updatedStudent));

		return ResponseEntity.ok(new StudentRequestResponseDto(student));
	}

}
