package com.notification.processor.dao.impl;

import com.notification.common.enums.NotificationStatus;
import com.notification.processor.dao.interfaces.NotificationDAO;
import com.notification.processor.dao.repositories.NotificationRepository;
import com.notification.processor.models.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class NotificationDAOImpl implements NotificationDAO {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification findById(UUID notificationID) {
        return notificationRepository.findById(notificationID).orElseThrow(() -> new RuntimeException("Notification " +
                "not found - " + notificationID));
    }

    @Override
    public void updateStatus(UUID notificationID, NotificationStatus status) {
        Notification notificationById = findById(notificationID);
        notificationById.setStatus(status);
        notificationRepository.save(notificationById);
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }
}
