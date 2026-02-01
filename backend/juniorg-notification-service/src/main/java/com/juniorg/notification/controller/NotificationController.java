package com.juniorg.notification.controller;

import com.juniorg.notification.dto.EmailRequest;
import com.juniorg.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest){

        log.info("email request received to controller");
        emailService.sendEmail(emailRequest);
        return ResponseEntity.ok("Email sent successfully");
    }
}
