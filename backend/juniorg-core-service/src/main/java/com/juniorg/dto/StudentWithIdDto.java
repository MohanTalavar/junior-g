package com.juniorg.dto;

import com.juniorg.pojos.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentWithIdDto {

    private Long id;            // ✅ Include ID
    private String firstName;
    private String middleName;
    private String surname;
    private String email;
    private String rollNumber;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String gender;
    private String fatherName;
    private String motherName;
    private String emergencyContact;
    private LocalDate admissionDate;
    private String bloodGroup;

    public StudentWithIdDto(Student s) {
        this.id = s.getId();  // ✅ crucial field
        this.firstName = s.getFirstName();
        this.middleName = s.getMiddleName();
        this.surname = s.getSurname();
        this.email = s.getEmail();
        this.rollNumber = s.getRollNumber();
        this.phoneNumber = s.getPhoneNumber();
        this.dateOfBirth = s.getDateOfBirth();
        this.gender = s.getGender();
        this.fatherName = s.getFatherName();
        this.motherName = s.getMotherName();
        this.emergencyContact = s.getEmergencyContact();
        this.admissionDate = s.getAdmissionDate();
        this.bloodGroup = s.getBloodGroup();
    }
}
