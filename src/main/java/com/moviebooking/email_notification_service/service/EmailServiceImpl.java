package com.moviebooking.email_notification_service.service;

import com.moviebooking.email_notification_service.event.BookingConfirmedEvent;
import com.moviebooking.email_notification_service.event.OtpRequest;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.format.DateTimeFormatter;
import java.util.Base64;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final Resend resend;
    private final SpringTemplateEngine templateEngine;
    private final RestTemplate restTemplate;

    @Value("${resend.from}")
    private String fromEmail;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");

    @Override
    public void sendOtpEmail(OtpRequest event) {
        String html = """
            <div style="font-family: Arial, sans-serif; padding:20px;">
                <h2>Verification Code</h2>
                <p>Your OTP code is:</p>
                <h1 style="letter-spacing:5px;color:#4CAF50;">%s</h1>
                <p>This OTP expires in 5 minutes.</p>
            </div>
            """.formatted(event.otpCode());

        sendViaResend(event.email(), "🔑 Your Verification Code", html);
    }

    @Override
    public void sendBookingConfirmationEmail(BookingConfirmedEvent event) {
        sendViaResend(event.email(), "🎬 Booking Confirmed | " + event.movie(), generateHtml(event));
    }

    private void sendViaResend(String to, String subject, String html) {
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(fromEmail)
                .to(to)
                .subject(subject)
                .html(html)
                .build();
        try {
            CreateEmailResponse response = resend.emails().send(params);
            log.info("Email sent to {} (id={})", to, response.getId());
        } catch (ResendException ex) {
            log.error("Failed sending email to {}", to, ex);
        }
    }
//    private final Environment environment;
//    private final RestTemplate restTemplate;

//    @PostConstruct
//    public void verify() {
//        System.out.println("Username: " + environment.getProperty("MAIL_USERNAME"));
//        String password = environment.getProperty("MAIL_PASSWORD");
//
//        System.out.println("Username: " + environment.getProperty("MAIL_USERNAME"));
//        System.out.println("Password exists: " + (password != null));
//
//        if (password != null) {
//            System.out.println("Password length: " + password.length());
//        }
//    }

//    @Override
//    public void sendBookingConfirmationEmail(
//            BookingConfirmedEvent event) {
//
//        try {
//            log.info("QR URL: '{}'", event.qrUrl());
//
//
//            byte[] qrImage =
//                    restTemplate.getForObject(
//                            event.qrUrl(),
//                            byte[].class
//                    );
//            log.info("QR URL received: '{}'", event.qrUrl());
//            MimeMessage message =
//                    mailSender.createMimeMessage();
//
//            MimeMessageHelper helper =
//                    new MimeMessageHelper(
//                            message,
//                            true,
//                            "UTF-8"
//                    );
//
//            helper.setTo(event.email());
//
//            helper.setSubject(
//                    "🎬 Booking Confirmed | "
//                            + event.movie()
//            );
//
//            helper.setText(
//                    generateHtml(event),
//                    true
//            );
//
//            DataSource dataSource =
//                    new ByteArrayDataSource(
//                            qrImage,
//                            "image/png"
//                    );
//
//            helper.addInline(
//                    "ticketQr",
//                    dataSource
//            );
//
//            mailSender.send(message);
//
//            log.info(
//                    "Booking confirmation email sent to {}",
//                    event.email()
//            );
//
//        }
//        catch (MessagingException | MailException ex) {
//
//            log.error(
//                    "Failed to send booking confirmation email",
//                    ex
//            );
//
//        }
//
//    }
//
//    @Override
//    public void sendOtpEmail(OtpRequest event) {
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
//
//            helper.setTo(event.email());
//            helper.setSubject("🔑 Your Verification Code / OTP");
//
//            // Simple HTML body for the OTP
//            String htmlContent = """
//                <div style="font-family: Arial, sans-serif; padding: 20px;">
//                    <h2>Verification Code</h2>
//                    <p>Your OTP code for login is:</p>
//                    <h1 style="color: #4CAF50; letter-spacing: 5px;">%s</h1>
//                    <p>This code will expire in 5 minutes. Do not share it with anyone.</p>
//                </div>
//                """.formatted(event.otpCode());
//
//            helper.setText(htmlContent, true);
//
//            mailSender.send(message);
//            log.info("OTP email successfully sent to {}", event.email());
//
//        } catch (MessagingException | MailException ex) {
//            log.error("Failed to send OTP email to {}", event.email(), ex);
//        }
//    }

    /**
     * Generates HTML using Thymeleaf.
     */
//    private String generateHtml(BookingConfirmedEvent event) {
//
//        Context context = new Context();
//
//        context.setVariable(
//                "bookingId",
//                event.bookingId()
//        );
//
//        context.setVariable(
//                "ticketNumber",
//                event.ticketNumber()
//        );
//
//        context.setVariable(
//                "userName",
//                event.userName()
//        );
//
//        context.setVariable(
//                "movieName",
//                event.movie()
//        );
//
//        context.setVariable(
//                "theatreName",
//                event.theatre()
//        );
//
//        context.setVariable(
//                "screenName",
//                event.screen()
//        );
//
//        context.setVariable(
//                "showTime",
//                event.showTime().format(FORMATTER)
//        );
//
//        context.setVariable(
//                "seats",
//                String.join(", ", event.seats())
//        );
//
//        context.setVariable(
//                "year",
//                java.time.Year.now().getValue()
//        );
//
//        return templateEngine.process(
//                "booking-confirmation",
//                context
//        );
//    }
    private String generateHtml(BookingConfirmedEvent event) {
        Context context = new Context();

        context.setVariable("bookingId", event.bookingId());
        context.setVariable("ticketNumber", event.ticketNumber());
        context.setVariable("userName", event.userName());
        context.setVariable("movieName", event.movie());
        context.setVariable("theatreName", event.theatre());
        context.setVariable("screenName", event.screen());
        context.setVariable("showTime", event.showTime().format(FORMATTER));
        context.setVariable("seats", String.join(", ", event.seats()));
        context.setVariable("qrUrl", event.qrUrl()); // <-- Added qrUrl context variable
        context.setVariable("year", java.time.Year.now().getValue());

        try {
            byte[] qrBytes = restTemplate.getForObject(event.qrUrl(), byte[].class);
            if (qrBytes != null) {
                String base64Qr = "data:image/png;base64," + Base64.getEncoder().encodeToString(qrBytes);
                context.setVariable("qrImage", base64Qr);
            }
        } catch (Exception ex) {
            log.error("Failed to download QR image from URL: {}", event.qrUrl(), ex);
            context.setVariable("qrImage", "");
        }
        return templateEngine.process("booking-confirmation", context);
    }

}