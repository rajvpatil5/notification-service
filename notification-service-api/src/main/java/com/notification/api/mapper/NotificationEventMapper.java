package com.notification.api.mapper;

import com.notification.api.models.entity.Notification;
import com.notification.common.event.NotificationCreatedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationEventMapper {

    public NotificationCreatedEvent toEvent(Notification notification) {

        return NotificationCreatedEvent.builder()
                .notificationId(notification.getNotificationId())
                .tenantId(notification.getTenantId().toString())
                .referenceId(notification.getReferenceId())
                .templateId(notification.getTemplateId())
                .channel(notification.getChannel())
                .recipient(notification.getRecipient())
                .priority(notification.getPriority())
                .payload(notification.getPayload())
                .scheduledAt(notification.getScheduledAt())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
