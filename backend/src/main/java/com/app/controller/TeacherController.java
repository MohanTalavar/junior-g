package com.app.controller;

import com.app.dto.TeacherRequestResponseDto;
import com.app.dto.TeacherWithCourseResponseDto;
import com.app.pojos.Teacher;
import com.app.service.ITeacherService;
import com.app.utils.InputStringSanitizer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.web.util.HtmlUtils.htmlEscape;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {
	private final ITeacherService teacherService;

	@GetMapping("/teacher-list")
	public ResponseEntity<List<TeacherWithCourseResponseDto>> getTeacherList(){

		List<TeacherWithCourseResponseDto> listOfTeachers = teacherService.retriveTeacherList();
		return ResponseEntity.ok(listOfTeachers);
	}

	@PostMapping("/assign-new-teacher-to-course/{courseName}")
	public ResponseEntity<String> assignNewTeacherToCourse(@PathVariable String courseName,
			@RequestBody TeacherRequestResponseDto transientTeacher) {
		String response = teacherService.addNewTeacher(InputStringSanitizer.sanitize(courseName), new Teacher(transientTeacher));
		return ResponseEntity.status(HttpStatus.CREATED).body(htmlEscape(response));
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

	@PutMapping("/update-teacher-details/{teacherId}")
	public ResponseEntity<TeacherRequestResponseDto> updateTeacherDetails(@PathVariable Long teacherId,
			@RequestBody TeacherRequestResponseDto updatedTeacher){
		
		Teacher teacher = teacherService.updateTeacherRecord(teacherId, new Teacher(updatedTeacher));
		
		return ResponseEntity.ok(new TeacherRequestResponseDto(teacher));
		
	}

}
