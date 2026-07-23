package com.moviebooking.email_notification_service.service;

import com.moviebooking.email_notification_service.event.BookingConfirmedEvent;

public interface EmailService {

    void sendBookingConfirmationEmail(BookingConfirmedEvent event);

}