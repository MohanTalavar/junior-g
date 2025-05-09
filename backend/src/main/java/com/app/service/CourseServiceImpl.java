package com.app.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.pojos.Course;
import com.app.repo.CourseRepo;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements ICourseService {

	private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

	@Autowired
	private CourseRepo courseRepo;

	@Override
	public String launchNewCourse(Course transientCourse) {

		log.info("Launching new course: {} with details: {}", transientCourse.getTitle(), transientCourse);
		Course persistentCourse = courseRepo.save(transientCourse);		
		if(persistentCourse == null) throw new ResourceNotFoundException("Course not added");
		log.info("Successfully launched new course: {}", persistentCourse.getTitle());
		return "Course: " + persistentCourse.getTitle()+" Added.";
	}

	@Override
	public List<Course> fetchCourses() {
		List<Course> courses = courseRepo.findAll();
		log.info("Fetched {} courses from the database", courses.size());
		return courses;
	}

	@Override
	public String removeCourse(String courseToBeRemoved) {

//		Course persistentCourse = courseRepo.findByTitle(courseToBeRemoved);
//
//		try {
//			courseRepo.delete(persistentCourse);
//		} catch (Exception e) {
//			System.out.println("error in removeCourse " + e.getMessage());
//		}
		
		// Method 2 by finder methods		
		log.info("Attempting to remove course with title: {}", courseToBeRemoved);
		courseRepo.deleteByTitle(courseToBeRemoved);
		return "Course" + courseToBeRemoved+ " is removed!!!";
	}

	@Override
	public Course getCourseDetails(String title) {
		log.info("Fetching course details for title: {}", title);
		Course persistentCourse = courseRepo.findByTitle(title);
		
		if(persistentCourse == null) throw new ResourceNotFoundException("Course not found" + title);
		
		return persistentCourse;
	}

	@Override
	public Course getCourseAndStudentDetails(String title) {
		log.info("In service layer  getCourseAndStudentDetails course : {}", title);
		Course persistentCourse = courseRepo.findByTitle(title);
		
		if(persistentCourse == null) throw new ResourceNotFoundException("Course not found" + title);
		// Its it 2nd Solution for the LazyInitializeException 
		// Here we access Only the size of the list in the session so that hibernate now loads the associated records/ students
		// This is also NOT a recommended sol, because here also hibernate needs to fire multiple queries
		log.info( "Fetching student size within session to solve LazyInitExp, size : {}",persistentCourse.getStudents().size());
		return persistentCourse;
	}

	@Override
	public Course getCourseAndStudentDetailsJoinFetch(String title) {

		log.info(" In service layer getCourseAndStudentDetailsJoinFetch course : {}", title);
		Course persistentCourse = courseRepo.findCourseWithStudentsByTitle(title);	
		
		if(persistentCourse == null) throw new ResourceNotFoundException("Course not found "+ title);
		
		return persistentCourse;
	}
}
