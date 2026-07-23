package com.moviebooking.email_notification_service.consumer;


import com.moviebooking.email_notification_service.event.BookingConfirmedEvent;
import com.moviebooking.email_notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingConfirmedConsumer {

    private final EmailService emailService;

    @KafkaListener(
            topics = "booking-confirmed",
            groupId = "notification-group"
    )
    public void consume(
            BookingConfirmedEvent event
    ) {

        log.info(
                "Received booking confirmation {}",
                event.bookingId()
        );

        emailService.sendBookingConfirmationEmail(
                event
        );
        log.info("Confirmation email sent to {}", event.email());
    }

}