package com.app.pojos;

import com.app.dto.EnquiryDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "enquiry_tbl")
public class Enquiry extends BaseEntity{

    @Column(length = 40)
    private String studentName;

    @Column(length = 40)
    private String parentName;

    @Column(length = 30, unique = true)
    private String emailId;

    @Column(length = 10)
    private String contactNo;

    @Column(length = 20)
    private String courseName;

    public Enquiry(EnquiryDto enquiryDto){
        this.studentName = enquiryDto.getStudentName();
        this.parentName = enquiryDto.getParentName();
        this.emailId = enquiryDto.getEmailId();
        this.contactNo = enquiryDto.getContactNo();
        this.courseName = enquiryDto.getCourseName();
    }

}
