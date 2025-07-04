package com.app.controller;

import com.app.dto.CourseRequestResponseDto;
import com.app.dto.CourseWithStudentsRequestDto;
import com.app.dto.CourseWithStudentsResponseDto;
import com.app.pojos.Course;
import com.app.service.ICourseService;
import com.app.utils.InputStringSanitizer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.web.util.HtmlUtils.htmlEscape;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final ICourseService courseService;

    @GetMapping("/get-courses")
    public ResponseEntity<List<CourseRequestResponseDto>> getCourses() {
        List<CourseRequestResponseDto> courses = courseService.fetchCourses().stream().map(CourseRequestResponseDto::new).toList();
        return ResponseEntity.ok(courses);
    }

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
        transientCourse.setTitle(InputStringSanitizer.sanitize(transientCourse.getTitle()));
        Course newCoursCourse = new Course(transientCourse);
        String response = courseService.launchNewCourse(newCoursCourse);
        return ResponseEntity.status(HttpStatus.CREATED).body(htmlEscape(response));
    }

    @DeleteMapping("/remove-course/{courseTitleToBeRemoved}")
    public ResponseEntity<String> removeExistingCourse(@PathVariable String courseTitleToBeRemoved) {

        String response = courseService.removeCourse(InputStringSanitizer.sanitize(courseTitleToBeRemoved));
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

        // return new CourseWithStudentsResponseDto(courseService.getCourseAndStudentDetailsJoinFetch(courseName));

        CourseWithStudentsResponseDto course = new CourseWithStudentsResponseDto(courseService.getCourseAndStudentDetailsJoinFetch(courseName));
        return ResponseEntity.ok(course);
    }

}
