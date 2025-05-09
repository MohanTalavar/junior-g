package com.app.controller;

import com.app.dto.EnquiryDto;
import com.app.pojos.Enquiry;
import com.app.service.IEnquiryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enquiry")
public class EnquiryController {

    @Autowired
    private IEnquiryService enquiryService;

    @PostMapping()
    public void storeAndAcknowledgeEnquiryDetails(@Valid @RequestBody EnquiryDto enquiryDto){
        enquiryService.saveEnquiryDetails(new Enquiry(enquiryDto));
    }

}
