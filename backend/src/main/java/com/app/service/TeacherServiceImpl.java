package com.app.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.pojos.Course;
import com.app.pojos.Teacher;
import com.app.repo.CourseRepo;
import com.app.repo.TeacherRepo;

@Transactional
@Service
public class TeacherServiceImpl implements ITeacherService {

	private static Logger log = LoggerFactory.getLogger(TeacherServiceImpl.class);

	@Autowired
	private TeacherRepo teacherRepo;

	@Autowired
	private CourseRepo courseRepo;

	@Override
	public String addNewTeacher(String courseName, Teacher newTeacher) {

		log.info("Adding new teacher :{} to course = {}", newTeacher.getFirstName(), courseName);
		
		if( newTeacher == null ) throw new IllegalArgumentException("Teacher details cannot be null!");

		Course persistentCourse = courseRepo.findByTitle(courseName);

		if (persistentCourse == null)
			throw new ResourceNotFoundException(" Adding Teacher failed!! Course not found " + courseName);
		
		if (teacherRepo.findByEmail(newTeacher.getEmail()).isPresent()) {
		    throw new IllegalStateException("Teacher with email '" + newTeacher.getEmail() + "' already exists!");
		}

		newTeacher.addCourse(persistentCourse);
		teacherRepo.save(newTeacher);	
		
		return "Teacher "+ newTeacher.getFirstName()+ " is assigned to course: "+ courseName;
	}

	@Override
	public Teacher retrieveTeacherDetails(Long teacherId) {

		log.info("Retrieving details for teacherId: {}", teacherId);

        return teacherRepo.findById(teacherId)
				.orElseThrow(()-> new ResourceNotFoundException("Teacher not found "+ teacherId));
	}

	@Override
	public String deleteTeacherRecord(Long teacherId) {

		log.info("Attempting to delete teacher record with teacherId: {}", teacherId);
						
		Teacher persistentTeacher = teacherRepo.findById(teacherId)
				.orElseThrow(()-> new ResourceNotFoundException("Teacher not found "+ teacherId));
		
		// Remove teacher from each associated course to maintain bidirectional integrity.
	    // Using a copy of the list to avoid ConcurrentModificationException.
	    List<Course> coursesCopy = new ArrayList<>(persistentTeacher.getCourses());
	    for (Course course : coursesCopy) {
	        persistentTeacher.removeCourse(course);
	    }
	    // Delete the teacher
	    teacherRepo.delete(persistentTeacher);
	    return "Teacher " + persistentTeacher.getFirstName() + " details are deleted successfully!" ;
	}

	@Override
	public Teacher updateTeacherRecord(Long teacherId, Teacher updatedTeacher) {

		log.info("Updating teacher record for teacher Id: {}", teacherId);
		Teacher persistentTeacher = teacherRepo.findById(teacherId)
				.orElseThrow(()-> new ResourceNotFoundException("Teacher not found." + teacherId));
		
		persistentTeacher.setFirstName(updatedTeacher.getFirstName());
		persistentTeacher.setLastName(updatedTeacher.getLastName());
		persistentTeacher.setEmail(updatedTeacher.getEmail());
		persistentTeacher.setPhoneNumber(updatedTeacher.getPhoneNumber());
		persistentTeacher.setQualification(updatedTeacher.getQualification());
		persistentTeacher.setDateOfJoining(updatedTeacher.getDateOfJoining());
		
		// This returns the updated object (changes will auto-flush on commit)
		// L1 cache
		return persistentTeacher;
	}

}
