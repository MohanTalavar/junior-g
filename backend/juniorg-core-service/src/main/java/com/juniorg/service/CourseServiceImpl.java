package com.juniorg.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.juniorg.custom_exception.ResourceNotFoundException;
import com.juniorg.pojos.Course;
import com.juniorg.repo.CourseRepo;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements ICourseService {

	private final CourseRepo courseRepo;

	@Override
	public String launchNewCourse(Course transientCourse) {

		log.info("Launching new course: {} with details: {}", transientCourse.getTitle(), transientCourse);

		Course persistentCourse = courseRepo.save(transientCourse);

		log.info("Successfully launched new course: {}", persistentCourse.getTitle());
		return "Course: " + persistentCourse.getTitle() + " Added.";
	}

	@Override
	public List<Course> fetchCourses() {

		log.info("Fetching the courses");

		List<Course> courses = courseRepo.findAll();
		log.info("Fetched {} courses", courses.size());
		return courses;
	}

	@Override
	public String removeCourse(String courseToBeRemoved) {

		log.info("Attempting to remove course with title: {}", courseToBeRemoved);

		courseRepo.deleteByTitle(courseToBeRemoved);
		return "Course " + courseToBeRemoved+ " is removed!";
	}

	@Override
	public Course getCourseDetails(String title) {

		log.info("Fetching course details for title: {}", title);

		return courseRepo.findByTitle(title)
				.orElseThrow(()-> new ResourceNotFoundException("Course NOT found: {}" + title));

	}

	@Override
	public Course getCourseAndStudentDetails(String title) {

		log.info("In service layer  getCourseAndStudentDetails course : {}", title);

		Course persistentCourse = courseRepo.findByTitle(title)
				.orElseThrow(()-> new ResourceNotFoundException("Course NOT found: " + title));

		// Its it 2nd Solution for the LazyInitializeException 
		// Here we access Only the size of the list in the session so that hibernate now loads the associated records/ students
		// This is also NOT a recommended sol, because here also hibernate needs to fire multiple queries
		log.info( "Fetching student size within session to solve LazyInitExp, size : {}",persistentCourse.getStudents().size());
		return persistentCourse;
	}

	@Override
	public Course getCourseAndStudentDetailsJoinFetch(String title) {

		log.info("Fetching course with join-fetch strategy for: {}", title);

		return courseRepo.findCourseWithStudentsByTitle(title)
				.orElseThrow(()-> new ResourceNotFoundException("Course NOT found:" + title));
	}
}
