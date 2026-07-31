package com.moviebooking.email_notification_service.event;

public record OtpEvent(
        String email,
        String otpCode
) {
}