package com.moviebooking.email_notification_service;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class EmailNotificationServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(EmailNotificationServiceApplication.class, args);
	}

}
