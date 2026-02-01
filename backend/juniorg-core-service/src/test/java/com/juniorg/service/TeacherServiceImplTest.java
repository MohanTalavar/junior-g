package com.juniorg.service;

import com.juniorg.custom_exception.ResourceNotFoundException;
import com.juniorg.pojos.Course;
import com.juniorg.pojos.Teacher;
import com.juniorg.repo.CourseRepo;
import com.juniorg.repo.TeacherRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceImplTest {

    @Mock
    private TeacherRepo teacherRepo;

    @Mock
    private CourseRepo courseRepo;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    private Teacher teacher;
    private Course course;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacher.setEmail("john.doe@example.com");

        course = new Course();
        course.setId(1L);
        course.setTitle("Math");
    }

    @Test
    @DisplayName("retrieveTeacherList: should return sorted list of teachers")
    void testRetrieveTeacherList() {
        List<Teacher> teacherList = List.of(teacher);
        when(teacherRepo.findAll()).thenReturn(teacherList);

        List<?> result = teacherService.retrieveTeacherList();

        assertEquals(1, result.size());
        verify(teacherRepo).findAll();
    }

    @Test
    @DisplayName("addNewTeacher: should add teacher to course successfully")
    void testAddNewTeacherSuccess() {
        when(courseRepo.findByTitle("Math")).thenReturn(Optional.of(course));
        when(teacherRepo.findByEmail(teacher.getEmail())).thenReturn(Optional.empty());

        String result = teacherService.addNewTeacher("Math", teacher);

        assertTrue(result.contains("John"));
        verify(teacherRepo).save(teacher);
        verify(courseRepo).findByTitle("Math");
    }


    @Test
    @DisplayName("addNewTeacher: should throw exception if course not found")
    void testAddNewTeacherCourseNotFound() {
        when(courseRepo.findByTitle("Math")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                teacherService.addNewTeacher("Math", teacher)
        );
    }

    @Test
    @DisplayName("addNewTeacher: should throw exception if teacher email exists")
    void testAddNewTeacherEmailExists() {
        when(courseRepo.findByTitle("Math")).thenReturn(Optional.of(course));
        when(teacherRepo.findByEmail(teacher.getEmail())).thenReturn(Optional.of(teacher));

        assertThrows(IllegalStateException.class, () ->
                teacherService.addNewTeacher("Math", teacher)
        );
    }

    @Test
    @DisplayName("retrieveTeacherDetails: should return teacher if found")
    void testRetrieveTeacherDetailsSuccess() {
        when(teacherRepo.findById(1L)).thenReturn(Optional.of(teacher));

        Teacher result = teacherService.retrieveTeacherDetails(1L);

        assertEquals("John", result.getFirstName());
        verify(teacherRepo).findById(1L);
    }

    @Test
    @DisplayName("retrieveTeacherDetails: should throw exception if teacher not found")
    void testRetrieveTeacherDetailsNotFound() {
        when(teacherRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                teacherService.retrieveTeacherDetails(1L)
        );
    }

    @Test
    @DisplayName("deleteTeacherRecord: should remove teacher and courses successfully")
    void testDeleteTeacherRecord() {
        Course course2 = new Course();
        course2.setTitle("Science");
        teacher.getCourses().add(course);
        teacher.getCourses().add(course2);

        when(teacherRepo.findById(1L)).thenReturn(Optional.of(teacher));

        String result = teacherService.deleteTeacherRecord(1L);

        assertTrue(result.contains("John"));
        verify(teacherRepo).delete(teacher);
    }

    @Test
    @DisplayName("updateTeacherRecord: should update teacher fields successfully")
    void testUpdateTeacherRecord() {
        when(teacherRepo.findById(1L)).thenReturn(Optional.of(teacher));

        Teacher updated = new Teacher();
        updated.setFirstName("Jane");
        updated.setLastName("Smith");
        updated.setEmail("jane.smith@example.com");
        updated.setPhoneNumber("1234567890");

        Teacher result = teacherService.updateTeacherRecord(1L, updated);

        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("jane.smith@example.com", result.getEmail());
        verify(teacherRepo).findById(1L);
    }

}
