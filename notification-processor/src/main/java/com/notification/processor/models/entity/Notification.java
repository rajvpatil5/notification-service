package com.notification.processor.models.entity;

import com.notification.common.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "notifications")
public class Notification extends AbstractEntity {

    @Id
    @Column(name = "notification_id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Getter
    @Setter
    private NotificationStatus status;

}
