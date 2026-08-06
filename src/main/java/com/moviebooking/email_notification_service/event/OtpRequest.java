package com.moviebooking.email_notification_service.event;
public record OtpRequest(
        String email,
        String otpCode
) {}