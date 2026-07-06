package com.notification.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationProcessorApplication {

    static void main(String[] args) {
        SpringApplication.run(NotificationProcessorApplication.class, args);
    }

    public void agenda1() {
        // Notification processor repo setup
        // Generic sdk use case
        // Ingest - Kafka consumer implementation and use case
        // Dynamic notification use case and overview - Notification prioritization, Notification schedule
        // DLQ - Dead letter queue - Topic confusion
    }

    public void agenda2() {
        // Enable global SDK
        // Remove MDC from filter
        // Refactor base exception handler
        // Refactor controller for generic response
        // Test api response structure
    }
}
