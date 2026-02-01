package com.juniorg.client;

import com.juniorg.client.dto.EmailNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationClient {

    private final RestTemplate restTemplate;

    @Value("${notification.service.url}")
    private String notificationServiceUrl;

    public void sendEmail(String to, String subject, String body) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // This is a record
            EmailNotificationRequest payload =
                    new EmailNotificationRequest(to, subject, body);

            HttpEntity<EmailNotificationRequest> request =
                    new HttpEntity<>(payload, headers);

            String url = notificationServiceUrl + "/api/notifications/email";

            restTemplate.postForEntity(url, request, Void.class);
            log.info("Email sent via Notification Microservice to {}", to);

        } catch (Exception ex) {
            log.error("Notification service failed for {}: {}", to, ex.getMessage());
            throw ex; // important let caller decide fallback
        }
    }
}
