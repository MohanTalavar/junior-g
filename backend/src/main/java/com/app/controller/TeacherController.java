package com.app.controller;

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

import com.app.dto.TeacherRequestResponseDto;
import com.app.pojos.Teacher;
import com.app.service.ITeacherService;
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/teachers")
public class TeacherController {

	@Autowired
	private ITeacherService teacherService;

	@PostMapping("/assign-new-teacher-to-course/{courseName}")
	public ResponseEntity<String> assignNewTeacherToCourse(@PathVariable String courseName,
			@RequestBody TeacherRequestResponseDto transientTeacher) {

		String response = teacherService.addNewTeacher(courseName, new Teacher(transientTeacher));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}

	@GetMapping("/get-teacher-details/{teacherId}")
	public ResponseEntity<TeacherRequestResponseDto> getTeacherDetails(@PathVariable Long teacherId) {

		TeacherRequestResponseDto teacher = new TeacherRequestResponseDto(
				teacherService.retrieveTeacherDetails(teacherId));
		return ResponseEntity.ok(teacher);

	}

	@DeleteMapping("/delete-teacher-details/{teacherId}")
	public ResponseEntity<String> deleteTeacherDetails(@PathVariable Long teacherId) {

		String response = teacherService.deleteTeacherRecord(teacherId);
		return ResponseEntity.ok(response);

	}

	@PutMapping("/udpate-teacher-details/{teacherId}")
	public ResponseEntity<TeacherRequestResponseDto> updateTeacherDetails(@PathVariable Long teacherId,
			@RequestBody TeacherRequestResponseDto updatedTeacher){
		
		Teacher teacher = teacherService.updateTeacherRecord(teacherId, new Teacher(updatedTeacher));
		
		return ResponseEntity.ok(new TeacherRequestResponseDto(teacher));
		
	}

}
