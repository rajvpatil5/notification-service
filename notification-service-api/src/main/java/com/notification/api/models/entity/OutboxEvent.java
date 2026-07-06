package com.notification.api.models.entity;

import com.notification.api.enums.EventType;
import com.notification.api.enums.OutboxStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "NOTIFICATION_OUTBOX")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "EVENT_ID", nullable = false, updatable = false)
    private UUID eventId;

    /**
     * Notification ID.
     */
    @Column(name = "AGGREGATE_ID", nullable = false)
    private UUID aggregateId;

    /**
     * Example: NotificationCreatedEvent
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "EVENT_TYPE", nullable = false, length = 50)
    private EventType eventType;

    /**
     * Serialized NotificationCreatedEvent JSON.
     */
    @Lob
    @Column(name = "PAYLOAD", nullable = false)
    private String payload;

    /**
     * NEW PUBLISHED FAILED
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private OutboxStatus status;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "PUBLISHED_AT")
    private LocalDateTime publishedAt;

    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();

        if (status == null) {
            status = OutboxStatus.NEW;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}