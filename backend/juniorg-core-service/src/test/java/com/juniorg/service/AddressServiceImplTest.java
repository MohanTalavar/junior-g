package com.juniorg.service;

import com.juniorg.custom_exception.ResourceNotFoundException;
import com.juniorg.pojos.Address;
import com.juniorg.pojos.Student;
import com.juniorg.pojos.Teacher;
import com.juniorg.repo.StudentRepo;
import com.juniorg.repo.TeacherRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private TeacherRepo teacherRepo;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Student student;
    private Teacher teacher;
    private Address address;

    @BeforeEach
    void setup(){
        address = new Address();
        address.setCity("Pune");
        address.setState("Maharashtra");
        address.setCountry("India");
        address.setZipCode("411001");

        student = new Student();
        student.setId(1L);
        student.setFirstName("Mohan");

        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Priya");
    }

    @Test
    @DisplayName("addOrUpdateStudentAddress: should save new address when none exists")
    void addOrUpdateStudentAddress_ShouldSaveNewAddress_WhenNoneExists() {
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));

        String result = addressService.addOrUpdateStudentAddress(1L, address);

        assertEquals("Address details saved for Mohan", result);
        assertEquals(address, student.getAddress());
    }

    @Test
    @DisplayName("addOrUpdateStudentAddress: should update existing address")
    void addOrUpdateStudentAddress_ShouldUpdateExistingAddress() {
        Address existing = new Address();
        existing.setCity("Mumbai");
        student.setAddress(existing);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));

        String result = addressService.addOrUpdateStudentAddress(1L, address);

        assertEquals("Address details saved for Mohan", result);
        assertEquals("Pune", student.getAddress().getCity());
    }

    @Test
    @DisplayName("addOrUpdateStudentAddress: should throw when student not found")
    void addOrUpdateStudentAddress_ShouldThrow_WhenStudentNotFound() {
        when(studentRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> addressService.addOrUpdateStudentAddress(1L, address));
    }

    // ===== TEACHER ADDRESS TESTS =====

    @Test
    @DisplayName("addOrUpdateTeacherAddress: should save new address when none exists")
    void addOrUpdateTeacherAddress_ShouldSaveNewAddress_WhenNoneExists() {
        when(teacherRepo.findById(1L)).thenReturn(Optional.of(teacher));

        String result = addressService.addOrUpdateTeacherAddress(1L, address);

        assertEquals("Address details saved for Priya", result);
        assertEquals(address, teacher.getAddress());
    }

    @Test
    @DisplayName("addOrUpdateTeacherAddress: should update existing address")
    void addOrUpdateTeacherAddress_ShouldUpdateExistingAddress() {
        Address existing = new Address();
        existing.setCity("Delhi");
        teacher.setAddress(existing);
        when(teacherRepo.findById(1L)).thenReturn(Optional.of(teacher));

        String result = addressService.addOrUpdateTeacherAddress(1L, address);

        assertEquals("Address details saved for Priya", result);
        assertEquals("Pune", teacher.getAddress().getCity());
    }

    @Test
    @DisplayName("addOrUpdateTeacherAddress: should throw when teacher not found")
    void addOrUpdateTeacherAddress_ShouldThrow_WhenTeacherNotFound() {
        when(teacherRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> addressService.addOrUpdateTeacherAddress(1L, address));
    }


}
