package com.app.service;



import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.pojos.Course;
import com.app.pojos.Student;
import com.app.repo.CourseRepo;
import com.app.repo.StudentRepo;
import com.app.utils.UpdateUtils;

@Service
@Transactional
public class StudentServiceImpl implements IStudentService {

	@Autowired
	private StudentRepo studRepo;

	@Autowired
	private CourseRepo courseRepo;
	
	@Override
	public Student getStudentDetails(Long StudId) {
		
		System.out.println("In Student service layer getStudentDetails");
		Student persistentStud = studRepo.findById(StudId)
				.orElseThrow(()-> new ResourceNotFoundException("Student not found "+ StudId));
		
		return persistentStud;
	}

	@Override
	public Student admitNewStudent(String courseName, Student stud) {
		
		System.out.println("In Student service layer admitNewStudent");
		Course persistentCourse = courseRepo.findByTitle(courseName);
		if (persistentCourse == null) {
			throw new ResourceNotFoundException("Course not found: " + courseName);
		}
		// check if the student is already enrolled (Assuming student has unique email)
		boolean studentExists = persistentCourse.getStudents().stream()
				.anyMatch(s -> s.getRollNumber().equals(stud.getRollNumber()));

		if (studentExists)
			throw new IllegalStateException("Student is already enrolled in the course");

		// WITHOUT Helper methods
//			stud.setChoosenCourse(persistentCourse);// which link are we establishing here? s -->c
//			
//			/* need to know about the importance			 
//			 *  DOUBT ? Ans: as we are establishing bi - dir its imp that we link the ref from both sides	 
//			 * */
//			persistentCourse.getStudents().add(stud);// which link are we establishing here ? c-->s 

		// Lets use the HELPER/ CONVENIENCE methods from the Course POJO

		persistentCourse.addStudent(stud);

		// Student persistentStud = studRepo.save(stud); || No longer req : since added
		// cascading !!!
		// stud will be save by Automatic Dirty Checking

		// return "Student " + persistentStud.getName() + " enrolled in " +
		// persistentCourse.getTitle();
		return stud;

	}

	@Override
	public String cancelStudentAdmission(String courseName, Long studId) {

		System.out.println("In Student service layer cancelStudentAdmission");
		Student persistentStud = studRepo.findById(studId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studId));

		Course persistentCouse = courseRepo.findByTitle(courseName);

		if (persistentCouse == null) {
			throw new ResourceNotFoundException("Course not found: " + courseName);
		}
		persistentCouse.removeStudent(persistentStud);
		return "Student admission cancelled.";

	}

	@Override
	public Student updateStudentRecord(Long studId, Student updatedStudent) {
		
		System.out.println("In Student service layer udpateStudentRecord");
		
		Student persistentStud = studRepo.findById(studId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studId));
		
		UpdateUtils.updateIfNonNullAndDifferent(updatedStudent.getFirstName(), persistentStud::getFirstName, persistentStud::setFirstName);
	    UpdateUtils.updateIfNonNullAndDifferent(updatedStudent.getMiddleName(), persistentStud::getMiddleName, persistentStud::setMiddleName);
	    UpdateUtils.updateIfNonNullAndDifferent(updatedStudent.getSurname(), persistentStud::getSurname, persistentStud::setSurname);
	    UpdateUtils.updateIfNonNullAndDifferent(updatedStudent.getEmail(), persistentStud::getEmail, persistentStud::setEmail);
	    UpdateUtils.updateIfNonNullAndDifferent(updatedStudent.getRollNumber(), persistentStud::getRollNumber, persistentStud::setRollNumber);
	    UpdateUtils.updateIfNonNullAndDifferent(updatedStudent.getPhoneNumber(), persistentStud::getPhoneNumber, persistentStud::setPhoneNumber);
	    UpdateUtils.updateIfNonNullAndDifferent(updatedStudent.getDateOfBirth(), persistentStud::getDateOfBirth, persistentStud::setDateOfBirth);
	    UpdateUtils.updateIfNonNullAndDifferent(updatedStudent.getGender(), persistentStud::getGender, persistentStud::setGender);
	    UpdateUtils.updateIfNonNullAndDifferent(updatedStudent.getFatherName(), persistentStud::getFatherName, persistentStud::setFatherName);
	    UpdateUtils.updateIfNonNullAndDifferent(updatedStudent.getMotherName(), persistentStud::getMotherName, persistentStud::setMotherName);
	    UpdateUtils.updateIfNonNullAndDifferent(updatedStudent.getEmergencyContact(), persistentStud::getEmergencyContact, persistentStud::setEmergencyContact);
	    UpdateUtils.updateIfNonNullAndDifferent(updatedStudent.getAdmissionDate(), persistentStud::getAdmissionDate, persistentStud::setAdmissionDate);
	    UpdateUtils.updateIfNonNullAndDifferent(updatedStudent.getBloodGroup(), persistentStud::getBloodGroup, persistentStud::setBloodGroup);
		return persistentStud;
	}

}
