package com.notification.api.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.api.dao.interfaces.TemplateDAO;
import com.notification.api.dao.repositories.NotificationRepository;
import com.notification.api.dao.repositories.OutboxEventRepository;
import com.notification.api.enums.EventType;
import com.notification.api.exception.ValidationException;
import com.notification.api.mapper.NotificationEventMapper;
import com.notification.api.mapper.NotificationMapper;
import com.notification.api.models.entity.Notification;
import com.notification.api.models.entity.OutboxEvent;
import com.notification.api.models.entity.Template;
import com.notification.api.models.request.SendNotificationRequest;
import com.notification.api.services.interfaces.NotificationService;
import com.notification.api.utils.CommonUtils;
import com.notification.common.event.NotificationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.notification.api.constants.ErrorConstants.TEMPLATE_NOT_EXIST_WITH_ID_ERROR;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    public final TemplateDAO templateDAO;

    private final NotificationRepository notificationRepository;

    private final OutboxEventRepository outboxEventRepository;

    private final NotificationEventMapper notificationEventMapper;

    private final ObjectMapper objectMapper;

    private final NotificationMapper notificationMapper;

    @Override
    public void sendNotification(SendNotificationRequest notificationRequest) {
        Optional<Template> template =
                templateDAO.findByTenantIdAndId(
                        CommonUtils.getCurrentTenantId(),
                        notificationRequest.getTemplateId());

        if (template.isEmpty()) {

            // TODO Publish audit event later

            throw new ValidationException(
                    TEMPLATE_NOT_EXIST_WITH_ID_ERROR,
                    HttpStatus.BAD_REQUEST.value());

        }

        /*
         * Step 1
         * Save Notification
         */
        Notification notification =
                notificationMapper.toEntity(notificationRequest);

        notification = notificationRepository.save(notification);

        /*
         * Step 2
         * Create Domain Event
         */
        NotificationCreatedEvent event =
                notificationEventMapper.toEvent(notification);
        /*
         * Step 3
         * Serialize Event
         */
        String payload;

        try {

            payload = objectMapper.writeValueAsString(event);

        } catch (Exception ex) {

            throw new RuntimeException("Unable to serialize NotificationCreatedEvent.", ex);

        }

        /*
         * Step 4
         * Save Outbox Event
         */
        OutboxEvent outboxEvent =
                OutboxEvent.builder()
                        .aggregateId(notification.getNotificationId())
                        .eventType(EventType.NOTIFICATION_CREATED)
                        .payload(payload)
                        .build();

        outboxEventRepository.save(outboxEvent);

    }
}
