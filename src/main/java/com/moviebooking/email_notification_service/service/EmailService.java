package com.moviebooking.email_notification_service.service;

import com.moviebooking.email_notification_service.event.BookingConfirmedEvent;
import com.moviebooking.email_notification_service.event.OtpEvent;

public interface EmailService {

    void sendBookingConfirmationEmail(BookingConfirmedEvent event);
    void sendOtpEmail(OtpEvent event);
}