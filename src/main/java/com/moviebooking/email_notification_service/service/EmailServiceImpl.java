package com.moviebooking.email_notification_service.service;

import com.moviebooking.email_notification_service.event.BookingConfirmedEvent;
import com.moviebooking.email_notification_service.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");

    @Override
    public void sendBookingConfirmationEmail(BookingConfirmedEvent event) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(event.email());

            helper.setSubject(
                    "🎬 Booking Confirmed | " + event.movie()
            );

            helper.setText(generateHtml(event), true);

            mailSender.send(message);

            log.info(
                    "Booking confirmation email sent to {}",
                    event.email()
            );

        } catch (MessagingException | MailException ex) {

            log.error(
                    "Failed to send booking confirmation email to {}",
                    event.email(),
                    ex
            );

            // Later we'll publish to Retry Topic / DLT here
        }
    }

    /**
     * Generates HTML using Thymeleaf.
     */
    private String generateHtml(BookingConfirmedEvent event) {

        Context context = new Context();

        context.setVariable(
                "bookingId",
                event.bookingId()
        );

        context.setVariable(
                "ticketNumber",
                event.ticketNumber()
        );

        context.setVariable(
                "userName",
                event.userName()
        );

        context.setVariable(
                "movieName",
                event.movie()
        );

        context.setVariable(
                "theatreName",
                event.theatre()
        );

        context.setVariable(
                "screenName",
                event.screen()
        );

        context.setVariable(
                "showTime",
                event.showTime().format(FORMATTER)
        );

        context.setVariable(
                "seats",
                String.join(", ", event.seats())
        );

        context.setVariable(
                "year",
                java.time.Year.now().getValue()
        );

        return templateEngine.process(
                "booking-confirmation",
                context
        );
    }

}