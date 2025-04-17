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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.CourseRequestResponseDto;
import com.app.dto.CourseWithStudentsRequestDto;
import com.app.dto.CourseWithStudentsResponseDto;
import com.app.pojos.Course;
import com.app.service.ICourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	private ICourseService courseService;

	@PostMapping("/add-new-course")
	public ResponseEntity<String> addNewCourse(@Valid @RequestBody CourseRequestResponseDto transientCourse) {

		String response = courseService.launchNewCourse(new Course(transientCourse));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/add-new-course-with-students")
	public ResponseEntity<String> addNewCourseWithStudDetails(
			@RequestBody CourseWithStudentsRequestDto transientCourse) {

		/*
		 * Student newStud1 = new Student( new
		 * StudentRequestDto("abc1","abc1@gmail.com")); Student newStud2 = new Student(
		 * new StudentRequestDto("abc2","abc1@gmail.com")); Student newStud3 = new
		 * Student( new StudentRequestDto("abc3","abc1@gmail.com"));
		 */
		Course newCoursCourse = new Course(transientCourse);
		String response = courseService.launchNewCourse(newCoursCourse);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/remove-course/{courseTitleToBeRemoved}")
	public ResponseEntity<String> removeExistingCourse(@PathVariable String courseTitleToBeRemoved) {

		String response = courseService.removeCourse(courseTitleToBeRemoved);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/get-course-details/{title}")
	public ResponseEntity<CourseRequestResponseDto> getCourseDetails(@PathVariable String title) {

		/*
		 * Here we were checking for the lazy init exception When we fetch the course
		 * details the student list is null due to the proxy When we try to fetch the
		 * students we'll receive the lazy init excp Course detachedCourse =
		 * courseService.getCourseDetails(title);
		 * detachedCourse.getStudents().forEach(System.out::println); return null;
		 */

		CourseRequestResponseDto course = new CourseRequestResponseDto(courseService.getCourseDetails(title));
		return ResponseEntity.ok(course);
	}

	@GetMapping("/get-course-and-student-details/{courseName}")
	public ResponseEntity<CourseWithStudentsResponseDto> getCourseAndStudentDetails(@PathVariable String courseName) {

		/*
		 * Solution 2 for lazy init exception CourseWithStudentsResponseDto dto = null;
		 * dto = new
		 * CourseWithStudentsResponseDto(courseService.getCourseAndStudentDetails(
		 * courseName)); // check if we have the access to the associated students
		 * dto.getStudents().forEach(System.out::println); return dto;
		 */

		CourseWithStudentsResponseDto course = new CourseWithStudentsResponseDto(
				courseService.getCourseAndStudentDetails(courseName));
		return ResponseEntity.ok(course);
	}

	@GetMapping("/get-course-and-student-details-join-fetch/{courseName}")
	public ResponseEntity<CourseWithStudentsResponseDto> getCourseAndStudentDetialsJoinFetch(
			@PathVariable String courseName) {

		// return new
		// CourseWithStudentsResponseDto(courseService.getCourseAndStudentDetailsJoinFetch(courseName));

		CourseWithStudentsResponseDto course = new CourseWithStudentsResponseDto(
				courseService.getCourseAndStudentDetailsJoinFetch(courseName));

		return ResponseEntity.ok(course);
	}

}
