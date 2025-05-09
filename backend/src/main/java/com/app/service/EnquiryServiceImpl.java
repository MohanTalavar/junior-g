package com.app.service;

import com.app.pojos.Enquiry;
import com.app.pojos.User;
import com.app.repo.EnquiryRepo;
import com.app.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EnquiryServiceImpl implements IEnquiryService {

    private static final Logger log = LoggerFactory.getLogger(EnquiryServiceImpl.class);
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Autowired
    private EnquiryRepo enquiryRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;

    @Override
    public void saveEnquiryDetails(Enquiry enquiry) {
        log.info("Registering new enquiry for admission: {}", enquiry);
        enquiryRepo.save(enquiry);

        // Prepare email content for admin
        String adminSubject = "New Admission Enquiry - Junior G International Preschool";
        String adminBody = String.format(
                "Dear Admin,\n\n" +
                        "A new admission enquiry has been received. Please find the details below:\n\n" +
                        "Student Name : %s\n" +
                        "Parent Name  : %s\n" +
                        "Email ID     : %s\n" +
                        "Contact No.  : %s\n" +
                        "Course Name  : %s\n\n" +
                        "Regards,\n" +
                        "Junior G International Preschool",
                enquiry.getStudentName(),
                enquiry.getParentName(),
                enquiry.getEmailId(),
                enquiry.getContactNo(),
                enquiry.getCourseName()
        );

        // Notify all admins
        log.info("Fetching admin details to notify about the admission enquiry");
        List<User> admins = userRepo.findByRole(ROLE_ADMIN);
        log.info("Sending email to the admin");
        admins.forEach(admin -> emailService.sendEmail(admin.getEmail(), adminSubject, adminBody));

        // Prepare acknowledgment email for parent
        String parentSubject = "Thank you for your enquiry - Junior G International Preschool";
        String parentBody = String.format(
                "Dear %s,\n\n" +
                        "Thank you for reaching out to Junior G International Preschool regarding the \"%s\" course for your child, %s.\n\n" +
                        "We have successfully received your enquiry. Our admissions team will connect with you shortly to discuss further.\n\n" +
                        "Regards,\n" +
                        "Junior G International Preschool",
                enquiry.getParentName(),
                enquiry.getCourseName(),
                enquiry.getStudentName()
        );

        log.info("Sending acknowledgment email to the parent.");
        emailService.sendEmail(enquiry.getEmailId(), parentSubject, parentBody);
    }

}
