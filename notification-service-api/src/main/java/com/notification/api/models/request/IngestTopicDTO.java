package com.notification.api.models.request;

import com.notification.common.enums.NotificationChannel;
import lombok.Data;

import java.util.Map;

@Data
public class IngestTopicDTO {

    private String requestId;

    private String tenantId;

    private Long receivedAt;

    private String templateId;

    private Map<String, Object> payload;

    private NotificationChannel notificationChannel;
}
