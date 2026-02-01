package com.juniorg.notification.service;

import com.juniorg.notification.dto.EmailRequest;

public interface EmailService {

    void sendEmail(EmailRequest emailRequest);
}
