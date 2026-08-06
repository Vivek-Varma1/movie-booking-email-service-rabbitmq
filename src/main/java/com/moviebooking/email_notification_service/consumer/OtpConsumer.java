package com.moviebooking.email_notification_service.consumer;

import com.moviebooking.email_notification_service.event.OtpRequest;
import com.moviebooking.email_notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OtpConsumer {

    private final EmailService emailService;

    public void consume(OtpRequest event) {
        log.info("Received OTP request for email: {}", event.email());
        emailService.sendOtpEmail(event);
    }
}