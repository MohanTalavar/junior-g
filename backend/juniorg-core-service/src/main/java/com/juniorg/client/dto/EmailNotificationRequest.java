package com.juniorg.client.dto;

public record EmailNotificationRequest(

        String to,
        String subject,
        String body
) {}
