package com.moviebooking.email_notification_service.controller;


import com.moviebooking.email_notification_service.event.OtpRequest;
import com.moviebooking.email_notification_service.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/otp")
    public ResponseEntity<Void> sendOtpEmail(
            @Valid @RequestBody OtpRequest request
    ) {

        emailService.sendOtpEmail(
               request
        );

        return ResponseEntity.ok().build();
    }
}