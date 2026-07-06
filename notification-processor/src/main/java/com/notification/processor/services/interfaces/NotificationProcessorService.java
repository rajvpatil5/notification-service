package com.notification.processor.services.interfaces;

import com.notification.common.event.NotificationCreatedEvent;

public interface NotificationProcessorService {
    void process (NotificationCreatedEvent event);
}
