package com.notification.processor.sender;

import com.notification.common.event.NotificationCreatedEvent;
import com.notification.processor.models.entity.Template;

public interface NotificationSender {

    void send(NotificationCreatedEvent event,
              Template template,
              String renderedContent);

}