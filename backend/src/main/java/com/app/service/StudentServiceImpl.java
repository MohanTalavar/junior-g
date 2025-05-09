package com.app.service;



import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);
	@Autowired
	private StudentRepo studRepo;

	@Autowired
	private CourseRepo courseRepo;

	@Override
	public Student getStudentDetails(Long studentId) {
		log.info("Fetching details for studentId: {}", studentId);

		Student persistentStud = studRepo.findById(studentId)
				.orElseThrow(() -> {
					log.warn("Student not found with id: {}", studentId);
					return new ResourceNotFoundException("Student not found " + studentId);
				});

		log.debug("Student details fetched successfully: {}", persistentStud.getId()); // optional
		return persistentStud;
	}

	@Override
	public Student admitNewStudent(String courseName, Student stud) {

		log.info("Admitting new student: name={}, course={}", stud.getFirstName(), courseName);
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

		log.info("Cancelling admission for studentId={} from course={}", studId, courseName);
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

		log.info("Updating student record for student Id: {}", studId);
		
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
		log.info("Successfully updated student record for studentId: {}", studId);
		return persistentStud;
	}

}