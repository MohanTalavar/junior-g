package com.juniorg.service;

import com.juniorg.pojos.Enquiry;
import com.juniorg.pojos.User;
import com.juniorg.repo.EnquiryRepo;
import com.juniorg.repo.UserRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.w3c.dom.stylesheets.LinkStyle;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EnquiryServiceImplTest {

    @Mock
    private EnquiryRepo enquiryRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EnquiryServiceImpl enquiryService;

    @Captor
    private ArgumentCaptor<String> subjectCaptor;

    @Captor
    private ArgumentCaptor<String> bodyCaptor;

    @Test
    void saveEnquiryDetails_ShouldSaveEnquiry_NotifyAdmins_AndNotifyParent(){

        // Arrange
        Enquiry enquiry = new Enquiry();
        enquiry.setStudentName("Junior");
        enquiry.setParentName("Mohan");
        enquiry.setEmailId("parent@gmail.com");
        enquiry.setContactNo("1234567890");
        enquiry.setCourseName("Nursery");

        User admin1 = new User();
        admin1.setEmail("admin1@gmail.com");

        User admin2 = new User();
        admin2.setEmail("admin2@gmail.com");

        List<User> admins = Arrays.asList(admin1,admin2);

        when(userRepo.findByRole("ROLE_ADMIN")).thenReturn(admins);

        // Act
        enquiryService.saveEnquiryDetails(enquiry);

        // Assert and Verify

        // Verify enquiry saved
        verify(enquiryRepo).save(enquiry);

        // Verify admin email sent twice
        verify(emailService).sendEmail(eq("admin1@gmail.com"), anyString(), anyString());
        verify(emailService).sendEmail(eq("admin2@gmail.com"), anyString(), anyString());

        // Verify parent acknowledgement sent once
        verify(emailService).sendEmail(eq("parent@gmail.com"), anyString(), anyString());

        // Verify admin lookup
        verify(userRepo).findByRole("ROLE_ADMIN");

    }

    @Test
    @DisplayName("Should NOT send parent acknowledgment if parent email is null")
    void saveEnquiryDetails_NullParentEmail_ShouldNotSendParentEmail(){

        // Arrange
        Enquiry enquiry = new Enquiry();
        enquiry.setStudentName("Junior");
        enquiry.setParentName("Mohan");
        enquiry.setEmailId(null); // <-- Key
        enquiry.setCourseName("1234567890");
        enquiry.setCourseName("Nursery");

        User admin = new User();
        admin.setEmail("admin@gmail.com");

        // Stub
        when(userRepo.findByRole("ROLE_ADMIN")).thenReturn(List.of(admin));

        // Act
        enquiryService.saveEnquiryDetails(enquiry);

        // Assert
        verify(emailService, times(0)).sendEmail(
                eq(null), anyString(), anyString()
        );

    }

    @Test
    @DisplayName("Should skip admin notifications when no admins exist")
    void saveEnquiryDetails_NullAdmins_ShouldNotSendAdminEmail(){

        // Arrange
        Enquiry enquiry = new Enquiry();
        enquiry.setStudentName("Junior");
        enquiry.setParentName("Mohan");
        enquiry.setEmailId("parent@gmail.com");
        enquiry.setCourseName("1234567890");
        enquiry.setCourseName("Nursery");

        // Stub
        when(userRepo.findByRole("ROLE_ADMIN")).thenReturn(new ArrayList<>());

        // Act
        enquiryService.saveEnquiryDetails(enquiry);

        // Assert
        // cuz only 1 email should be sent i.e to parent
        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());

    }

}
