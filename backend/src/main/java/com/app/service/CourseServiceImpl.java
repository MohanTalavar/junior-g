package com.app.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.pojos.Course;
import com.app.repo.CourseRepo;

@Service
@Transactional
public class CourseServiceImpl implements ICourseService {

	@Autowired
	private CourseRepo courseRepo;

	@Override
	public String launchNewCourse(Course transientCourse) {

		System.out.println("In service layer launchNewCourse");
		Course persistentCourse = courseRepo.save(transientCourse);		
		if(persistentCourse == null) throw new ResourceNotFoundException("Course not added");		
		return "Course: " + persistentCourse.getTitle()+" Added.";
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
		System.out.println("In service layer removeCourse");
		courseRepo.deleteByTitle(courseToBeRemoved);
		return "Course" + courseToBeRemoved+ " is removed!!!";
	}

	@Override
	public Course getCourseDetails(String title) {
		System.out.println("In service layer :: getCourseDetails");		
		Course persistentCourse = courseRepo.findByTitle(title);
		
		if(persistentCourse == null) throw new ResourceNotFoundException("Course not found" + title);
		
		return persistentCourse;
	}

	@Override
	public Course getCourseAndStudentDetails(String title) {
		System.out.println("In service layer :: getCourseAndStudentDetails");		
		Course persistentCourse = courseRepo.findByTitle(title);
		
		if(persistentCourse == null) throw new ResourceNotFoundException("Course not found" + title);
		// Its it 2nd Solution for the LazyInitializeException 
		// Here we access Only the size of the list in the session so that hibernate now loads the associated records/ students
		// This is also NOT a recommended sol, because here also hibernate needs to fire multiple queries
		System.out.println( "Fetching student size within session to solve LazyInitExp"+persistentCourse.getStudents().size());
		return persistentCourse;
	}

	@Override
	public Course getCourseAndStudentDetailsJoinFetch(String title) {
		
		System.out.println(" In service layer getCourseAndStudentDetailsJoinFetch");		
		Course persistentCourse = courseRepo.findCourseWithStudentsByTitle(title);	
		
		if(persistentCourse == null) throw new ResourceNotFoundException("Course not found "+ title);
		
		return persistentCourse;
	}
}
