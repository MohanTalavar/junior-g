package com.juniorg.service;

import com.juniorg.custom_exception.ResourceNotFoundException;
import com.juniorg.dto.TeacherWithCourseResponseDto;
import com.juniorg.pojos.Course;
import com.juniorg.pojos.Teacher;
import com.juniorg.repo.CourseRepo;
import com.juniorg.repo.TeacherRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements ITeacherService {

	private final TeacherRepo teacherRepo;
	private final CourseRepo courseRepo;
	private static final Logger log = LoggerFactory.getLogger(TeacherServiceImpl.class);


	@Override
	public List<TeacherWithCourseResponseDto> retrieveTeacherList() {

		log.info("Fetching the list of teachers");

		List<Teacher> teacherList =  teacherRepo.findAll();
		return teacherList.stream()
				.sorted(Comparator.comparing(t->t.getFirstName().toLowerCase()))
				.map(TeacherWithCourseResponseDto::new)
				.toList();
	}

	@Override
	public String addNewTeacher(String courseName, Teacher newTeacher) {

		log.info("Adding new teacher: {} to course: {}", newTeacher.getFirstName(), courseName);
		
		if( newTeacher == null )
			throw new IllegalArgumentException("Teacher details cannot be null!");

		Course persistentCourse = courseRepo.findByTitle(courseName)
				.orElseThrow(()-> new ResourceNotFoundException(" Adding Teacher failed! Course not found " + courseName));

		teacherRepo.findByEmail(newTeacher.getEmail())
				.ifPresent(t -> { throw new IllegalStateException("Teacher with email '" + t.getEmail() + "' already exists!"); });

		newTeacher.addCourse(persistentCourse);
		teacherRepo.save(newTeacher);	
		
		return "Teacher "+ newTeacher.getFirstName()+ " is assigned to course: "+ courseName;
	}

	@Override
	public Teacher retrieveTeacherDetails(Long teacherId) {

		log.info("Retrieving details for teacherId: {}", teacherId);

        return teacherRepo.findById(teacherId)
				.orElseThrow(()-> new ResourceNotFoundException("Teacher not found: "+ teacherId));
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
				.orElseThrow(()-> new ResourceNotFoundException("Teacher not found:" + teacherId));

		// Only update fields if non-null to avoid overwriting existing data
		if (updatedTeacher.getFirstName() != null) persistentTeacher.setFirstName(updatedTeacher.getFirstName());
		if (updatedTeacher.getLastName() != null) persistentTeacher.setLastName(updatedTeacher.getLastName());
		if (updatedTeacher.getEmail() != null) persistentTeacher.setEmail(updatedTeacher.getEmail());
		if (updatedTeacher.getPhoneNumber() != null) persistentTeacher.setPhoneNumber(updatedTeacher.getPhoneNumber());
		if (updatedTeacher.getQualification() != null) persistentTeacher.setQualification(updatedTeacher.getQualification());
		if (updatedTeacher.getDateOfJoining() != null) persistentTeacher.setDateOfJoining(updatedTeacher.getDateOfJoining());
		
		// This returns the updated object (changes will auto-flush on commit)
		// L1 cache
		return persistentTeacher;
	}

}
