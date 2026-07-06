package com.notification.processor.consumer;

import com.notification.common.event.NotificationCreatedEvent;
import com.notification.processor.services.interfaces.NotificationProcessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class NotificationConsumer {
    private final NotificationProcessorService notificationProcessorService;

    @KafkaListener(topics = {"${kafka.ingest.topic}"}, groupId = "INGESTION_PROCESSOR_GROUP")
    public void consume(NotificationCreatedEvent event) {
        log.info("Received notification {}", event.getNotificationId() );
        notificationProcessorService.process(event);
    }
}
