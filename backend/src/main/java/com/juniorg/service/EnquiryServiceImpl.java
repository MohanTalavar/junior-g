package com.juniorg.service;

import com.juniorg.pojos.Enquiry;
import com.juniorg.pojos.User;
import com.juniorg.repo.EnquiryRepo;
import com.juniorg.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EnquiryServiceImpl implements IEnquiryService {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final EnquiryRepo enquiryRepo;
    private final UserRepo userRepo;
    private final EmailService emailService;
    private static final Logger log = LoggerFactory.getLogger(EnquiryServiceImpl.class);

    @Override
    public void saveEnquiryDetails(Enquiry enquiry) {
        log.info("Registering new enquiry for admission: {}", enquiry);
        enquiryRepo.save(enquiry);

        // Prepare email content for admin
        String adminSubject = "New Admission Enquiry - Junior G International Preschool";
        String adminBody = prepareAdminBody(enquiry);

        // Notify all admins
        log.info("Fetching admin details to notify about the admission enquiry");
        List<User> admins = userRepo.findByRole(ROLE_ADMIN);

        // Check if we have admins
        if (!admins.isEmpty()) {
            log.info("Sending email to {} admin(s)", admins.size());
            admins.forEach(admin -> emailService.sendEmail(admin.getEmail(), adminSubject, adminBody));
        } else {
            log.warn("Skipping acknowledgment: Admins NOT found in system");
        }

        // Prepare acknowledgment email for parent
        String parentSubject = "Thank you for your enquiry - Junior G International Preschool";
        String parentBody = prepareParentBody(enquiry);

        // Test case failed for the null email if of parent
        // So adding the null check
        // We do not rely on other layers, as the request dto already has validation
        // But while unit testing its done in isolation
        // So securing every layer is imp

        if (enquiry.getEmailId() != null && !enquiry.getEmailId().isBlank()) {
            log.info("Sending acknowledgment email to the parent.");
            emailService.sendEmail(enquiry.getEmailId(), parentSubject, parentBody);
        } else {
            log.warn("Skipping acknowledgment: Parent email is null/blank");
        }

    }

    public static String prepareAdminBody(Enquiry enquiry) {

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

        return adminBody;
    }

    public static String prepareParentBody(Enquiry enquiry){

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

        return parentBody;
    }

}
