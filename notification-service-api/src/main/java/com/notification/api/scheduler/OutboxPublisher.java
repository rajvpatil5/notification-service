package com.notification.api.scheduler;

import com.notification.api.dao.repositories.OutboxEventRepository;
import com.notification.api.enums.OutboxStatus;
import com.notification.api.models.entity.OutboxEvent;
import com.notification.api.pubsub.publisher.GenericPublisher;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class OutboxPublisher {
    private final OutboxEventRepository outboxEventRepository;

    private final GenericPublisher genericPublisher;

    @Scheduled(fixedDelay = 5000) // run after every 5 sex
    @Transactional
    public void publishEvents() {
        List<OutboxEvent> events =
                outboxEventRepository.findTop100ByStatusOrderByCreatedAt(OutboxStatus.NEW);
        if (events.isEmpty()) {
        }

        log.info("Found {} outbox event(s) to publish.", events.size());
        for (OutboxEvent event : events) {
            try {
                genericPublisher.sendJsonToIngest(event.getPayload());
                event.setStatus(OutboxStatus.PUBLISHED);
                event.setPublishedAt(LocalDateTime.now());
                outboxEventRepository.save(event);
                log.info("Published event {}", event.getEventId());
            } catch (Exception e) {
                log.error("Failed to publish event {}", event.getEventId(), e);
                event.setStatus(OutboxStatus.FAILED);
                outboxEventRepository.save(event);
            }
        }
    }
}
