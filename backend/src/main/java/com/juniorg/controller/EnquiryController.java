package com.juniorg.controller;

import com.juniorg.dto.EnquiryDto;
import com.juniorg.pojos.Enquiry;
import com.juniorg.service.IEnquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enquiry")
public class EnquiryController {
    private final IEnquiryService enquiryService;

    @PostMapping
    public ResponseEntity<String> storeAndAcknowledgeEnquiryDetails(@Valid @RequestBody EnquiryDto enquiryDto){
        enquiryService.saveEnquiryDetails(new Enquiry(enquiryDto));
        return ResponseEntity.status(HttpStatus.CREATED).body("Enquiry stored successfully");
    }


}
