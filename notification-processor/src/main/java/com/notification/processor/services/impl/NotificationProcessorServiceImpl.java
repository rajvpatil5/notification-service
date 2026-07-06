package com.notification.processor.services.impl;

import com.notification.common.enums.NotificationStatus;
import com.notification.common.event.NotificationCreatedEvent;
import com.notification.processor.dao.interfaces.NotificationDAO;
import com.notification.processor.dao.interfaces.TemplateDAO;
import com.notification.processor.models.entity.Template;
import com.notification.processor.renderer.TemplateRenderer;
import com.notification.processor.renderer.TemplateValidator;
import com.notification.processor.sender.NotificationSender;
import com.notification.processor.services.interfaces.NotificationProcessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProcessorServiceImpl implements NotificationProcessorService {
    private final TemplateDAO templateDAO;

    private final TemplateRenderer templateRenderer;

    private final TemplateValidator templateValidator;

    private final NotificationSender notificationSender;

    private final NotificationDAO notificationDAO;

    @Override
    public void process(NotificationCreatedEvent event) {
        // Step 1 (Current)
        // Verify that the event is successfully received.
        log.info(
                "Processing notification. NotificationId={}, ReferenceId={}, Channel={}",
                event.getNotificationId(),
                event.getReferenceId(),
                event.getChannel()
        );
        notificationDAO.updateStatus(event.getNotificationId(), NotificationStatus.PROCESSING);
   
        try {
            // Step 2 (Next)
            // Load template from cache/database.
            Template template =
                    templateDAO.findByTenantIdAndId(event.getTenantId(), event.getTemplateId()).orElseThrow(() -> new RuntimeException("Template not found - " + event.getTemplateId()));
            log.info("Loaded -> template = {}", template);

            // Step 3
            // Validate template
            templateValidator.validate(template, event.getPayload());
            // Render template.
            String renderedHtml = templateRenderer.render(template.getMessageTemplate(), event.getPayload());
            log.info("renderedHtml = {}", renderedHtml);

            // Step 4
            // Send notification (Email/SMS/Push).
            notificationSender.send(event, template, renderedHtml);
            log.info("Email sent");

            // Step 5
            // Update notification status.
            notificationDAO.updateStatus(event.getNotificationId(), NotificationStatus.COMPLETED);

        } catch (RuntimeException e) {
            notificationDAO.updateStatus(event.getNotificationId(), NotificationStatus.FAILED);
            throw new RuntimeException(e);
        }
    }

}
