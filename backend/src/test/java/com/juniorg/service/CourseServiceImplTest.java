package com.juniorg.service;

import com.juniorg.custom_exception.ResourceNotFoundException;
import com.juniorg.pojos.Course;
import com.juniorg.repo.CourseRepo;
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
public class CourseServiceImplTest {

    @Mock
    private CourseRepo courseRepo;

    @InjectMocks
    private CourseServiceImpl courseService;

    private final Course course = new Course("Math");

    @Test
    @DisplayName("launchNewCourse: should save and return success message")
    void launchNewCourse_ShouldSaveCourseSuccessfully() {

        when(courseRepo.save(course)).thenReturn(course);

        String response = courseService.launchNewCourse(course);

        assertEquals("Course: Math Added.", response);
        verify(courseRepo, times(1)).save(course);
    }

    @Test
    @DisplayName("fetchCourses: should return a list of courses")
    void fetchCourses_ShouldReturnCourseList() {

        when(courseRepo.findAll()).thenReturn(List.of(course));

        List<Course> result = courseService.fetchCourses();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(courseRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("removeCourse: should delete course by title")
    void removeCourse_ShouldDeleteCourseByTitle() {

        doNothing().when(courseRepo).deleteByTitle("Math");

        String response = courseService.removeCourse("Math");

        assertEquals("Course Math is removed!", response);
        verify(courseRepo, times(1)).deleteByTitle("Math");
    }

    @Test
    @DisplayName("getCourseDetails: should return course when title exists")
    void getCourseDetails_ShouldReturnCourse_WhenFound() {

        when(courseRepo.findByTitle("Math")).thenReturn(Optional.of(course));

        Course result = courseService.getCourseDetails("Math");

        assertNotNull(result);
        assertEquals("Math", result.getTitle());
        verify(courseRepo, times(1)).findByTitle("Math");
    }

    @Test
    @DisplayName("getCourseDetails: should throw when course not found")
    void getCourseDetails_ShouldThrow_WhenNotFound() {

        when(courseRepo.findByTitle("Physics")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> courseService.getCourseDetails("Physics")
        );

        verify(courseRepo, times(1)).findByTitle("Physics");
    }

    @Test
    @DisplayName("getCourseAndStudentDetails: should initialize student list within session")
    void getCourseAndStudentDetails_ShouldReturnCourseAndLoadStudents() {

        when(courseRepo.findByTitle("Math")).thenReturn(Optional.of(course));

        Course result = courseService.getCourseAndStudentDetails("Math");

        assertNotNull(result);
        verify(courseRepo, times(1)).findByTitle("Math");

        // We call size(), lazy resolved — cannot verify list size here since students may be empty
        assertDoesNotThrow(() -> result.getStudents().size());
    }

    @Test
    @DisplayName("getCourseAndStudentDetails: should throw when not found")
    void getCourseAndStudentDetails_ShouldThrow_WhenNotFound() {

        when(courseRepo.findByTitle("Science")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> courseService.getCourseAndStudentDetails("Science")
        );

        verify(courseRepo, times(1)).findByTitle("Science");
    }
    @Test
    @DisplayName("getCourseAndStudentDetailsJoinFetch: should return course using join fetch")
    void getCourseAndStudentDetailsJoinFetch_ShouldReturnCourse() {

        when(courseRepo.findCourseWithStudentsByTitle("Math")).thenReturn(Optional.of(course));

        Course result = courseService.getCourseAndStudentDetailsJoinFetch("Math");

        assertNotNull(result);
        verify(courseRepo, times(1)).findCourseWithStudentsByTitle("Math");
    }

    @Test
    @DisplayName("getCourseAndStudentDetailsJoinFetch: should throw when not found")
    void getCourseAndStudentDetailsJoinFetch_ShouldThrow_WhenNotFound() {

        when(courseRepo.findCourseWithStudentsByTitle("Hindi")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> courseService.getCourseAndStudentDetailsJoinFetch("Hindi")
        );

        verify(courseRepo, times(1)).findCourseWithStudentsByTitle("Hindi");
    }
}
