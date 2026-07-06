package com.notification.api.mapper;

import com.notification.api.models.entity.Notification;
import com.notification.api.models.request.SendNotificationRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.notification.api.utils.CommonUtils.getCurrentTenantId;

@Component
public class NotificationMapper {

    public Notification toEntity(SendNotificationRequest request) {

        return Notification.builder()
                .tenantId(UUID.fromString(getCurrentTenantId()))
                .referenceId(request.getReferenceId())
                .templateId(request.getTemplateId())
                .channel(request.getNotificationChannel())
                .recipient(request.getRecipient())
                .priority(request.getPriority())
                .payload(request.getPayload())
                .scheduledAt(request.getScheduledAt())
                .build();
    }

}
