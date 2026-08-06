package com.moviebooking.email_notification_service.consumer;


import com.moviebooking.email_notification_service.config.RabbitMQConfig;
import com.moviebooking.email_notification_service.event.BookingConfirmedEvent;
import com.moviebooking.email_notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingConfirmedConsumer {

    private final EmailService emailService;

    @RabbitListener(
            queues = RabbitMQConfig.QUEUE
    )
    public void consume(BookingConfirmedEvent event) {

        emailService.sendBookingConfirmationEmail(event);

    }

}