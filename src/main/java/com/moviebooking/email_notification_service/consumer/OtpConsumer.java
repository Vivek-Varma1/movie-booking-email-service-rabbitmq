package com.moviebooking.email_notification_service.consumer;

import com.moviebooking.email_notification_service.event.OtpEvent;
import com.moviebooking.email_notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OtpConsumer {

    private final EmailService emailService;

    @KafkaListener(
            topics = "user-otp-topic",
            groupId = "email-otp-group-v3", // Updated to v3 to skip cached offset errors
            concurrency = "3"
    )
    public void consume(OtpEvent event) {
        log.info("Received OTP request for email: {}", event.email());
        emailService.sendOtpEmail(event);
    }
}