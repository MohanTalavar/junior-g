package com.juniorg.service;

import com.juniorg.custom_exception.ResourceNotFoundException;
import com.juniorg.pojos.Course;
import com.juniorg.pojos.Student;
import com.juniorg.repo.CourseRepo;
import com.juniorg.repo.StudentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Student Service Impl Unit Test")
public class StudentServiceImplTest {

    @Mock
    private StudentRepo studRepo;

    @Mock
    private CourseRepo courseRepo;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private Course course;

    @BeforeEach
    void setup() {

        student = new Student();
        student.setId(10L);
        student.setRollNumber("R001");
        student.setFirstName("Mohan");
        student.setEmail("mohan@gmail.com");

        course = new Course();
        course.setTitle("LKG");
        course.setStudents(new ArrayList<>());
    }

    @Test
    @DisplayName("getStudentDetails: should return student when roll number matches")
    void testGetStudentDetailsFound() {
        when(studRepo.findByRollNumber("R001")).thenReturn(Optional.of(student));

        Student result = studentService.getStudentDetails("R001");

        assertNotNull(result);
        assertEquals("R001", result.getRollNumber());
        verify(studRepo).findByRollNumber("R001");
    }

    @Test
    @DisplayName("getStudentDetails: should throw when student not found")
    void testGetStudentDetailsNotFound() {

        when(studRepo.findByRollNumber("R999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                studentService.getStudentDetails("R999")
        );
    }

    @Test
    @DisplayName("admitNewStudent: should admit student successfully")
    void testAdmitNewStudent() {
        when(courseRepo.findByTitle("LKG")).thenReturn(Optional.ofNullable(course));

        Student result = studentService.admitNewStudent("LKG", student);

        assertTrue(course.getStudents().contains(student));
        assertEquals("R001", result.getRollNumber());
    }

    @Test
    @DisplayName("admitNewStudent: should throw when course doesn't exist")
    void testAdmitNewStudentCourseNotFound() {
        when(courseRepo.findByTitle("UKG")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                studentService.admitNewStudent("UKG", student)
        );
    }

    @Test
    @DisplayName("admitNewStudent: should not allow duplicate enrollment")
    void testAdmitNewStudentAlreadyEnrolled() {
        course.getStudents().add(student);
        when(courseRepo.findByTitle("LKG")).thenReturn(Optional.ofNullable(course));

        assertThrows(IllegalStateException.class, () ->
                studentService.admitNewStudent("LKG", student)
        );
    }

    @Test
    @DisplayName("cancelStudentAdmission: should remove student successfully")
    void testCancelStudentAdmission() {

        course.getStudents().add(student);

        when(studRepo.findByRollNumber("R001")).thenReturn(Optional.of(student));
        when(courseRepo.findByTitle("LKG")).thenReturn(Optional.ofNullable(course));

        String result = studentService.cancelStudentAdmission("LKG", "R001");

        assertFalse(course.getStudents().contains(student));
        assertEquals("Student admission cancelled.", result);
    }

    @Test
    @DisplayName("cancelStudentAdmission: should throw when student not found")
    void testCancelStudentAdmissionStudentNotFound() {

        when(studRepo.findByRollNumber("R001")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                studentService.cancelStudentAdmission("LKG", "R001")
        );
    }

    @Test
    @DisplayName("cancelStudentAdmission: should throw when course not found")
    void testCancelStudentAdmissionCourseNotFound() {

        when(studRepo.findByRollNumber("R001")).thenReturn(Optional.of(student));
        when(courseRepo.findByTitle("LKG")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                studentService.cancelStudentAdmission("LKG", "R001")
        );
    }

    @Test
    @DisplayName("updateStudentRecord: should update student fields by id")
    void testUpdateStudentRecord() {

        Student patch = new Student();
        patch.setFirstName("Rahul");

        when(studRepo.findById(10L)).thenReturn(Optional.of(student));

        Student result = studentService.updateStudentRecord(10L, patch);

        assertEquals("Rahul", result.getFirstName());
    }

    @Test
    @DisplayName("updateStudentRecord: should throw when student not found")
    void testUpdateStudentRecordNotFound() {
        when(studRepo.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                studentService.updateStudentRecord(10L, new Student())
        );
    }

    @Test
    @DisplayName("updateStudentRecordByRollNo: should update student fields by roll number")
    void testUpdateStudentRecordByRollNo() {

        Student update = new Student();
        update.setEmail("new@email.com");

        when(studRepo.findByRollNumber("R001")).thenReturn(Optional.of(student));

        Student result = studentService.updateStudentRecordByRollNo("R001", update);

        assertEquals("new@email.com", result.getEmail());
    }

    @Test
    @DisplayName("updateStudentRecordByRollNo: should throw when student not found")
    void testUpdateStudentRecordByRollNoNotFound() {

        when(studRepo.findByRollNumber("R001")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                studentService.updateStudentRecordByRollNo("R001", new Student())
        );
    }

}
