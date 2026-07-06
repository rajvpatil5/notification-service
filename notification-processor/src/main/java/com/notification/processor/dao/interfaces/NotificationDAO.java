package com.notification.processor.dao.interfaces;

import com.notification.common.enums.NotificationStatus;
import com.notification.processor.models.entity.Notification;

import java.util.UUID;

public interface NotificationDAO {
    Notification findById(UUID id);

    void updateStatus(UUID notificationID, NotificationStatus status);

    Notification save(Notification notification);
}
