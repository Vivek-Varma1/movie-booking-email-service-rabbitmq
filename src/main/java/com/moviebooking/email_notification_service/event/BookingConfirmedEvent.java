package com.moviebooking.email_notification_service.event;

import java.time.LocalDateTime;
import java.util.List;

public record BookingConfirmedEvent(

        Long bookingId,

        Long ticketId,

        Long userId,

        String email,

        String userName,

        String ticketNumber,

        String movie,

        String theatre,

        String screen,

        LocalDateTime showTime,

        List<String> seats

) {
}