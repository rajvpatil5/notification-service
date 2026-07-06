package com.notification.api.models.entity;

import com.notification.api.enums.NotificationStatus;
import com.notification.api.utils.JsonMapConverter;
import com.notification.common.dto.Recipient;
import com.notification.common.enums.NotificationChannel;
import com.notification.common.enums.NotificationPriority;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID notificationId;

    @Column(name = "TENANT_ID", nullable = false)
    private UUID tenantId;

    /**
     * Business reference. Example: ORDER-1001 PAYMENT-5002
     */
    @Column(nullable = false, length = 100)
    private String referenceId;

    /**
     * Template identifier. Example: WELCOME_EMAIL OTP_SMS
     */
    @Column(nullable = false, length = 100)
    private String templateId;

    /**
     * EMAIL / SMS / PUSH
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationChannel channel;

    /**
     * Receiver.
     */
    @Column(nullable = false, length = 255)
    @Embedded
    private Recipient recipient;

    /**
     * LOW / MEDIUM / HIGH
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationPriority priority;

    /**
     * Notification lifecycle status.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    /**
     * Template variables.
     */
    @Lob
    @Column(name = "PAYLOAD", columnDefinition = "CLOB", nullable = false)
    @Convert(converter = JsonMapConverter.class)
    private Map<String, Object> payload;

    /**
     * Null means send immediately.
     */
    private LocalDateTime scheduledAt;

    /**
     * Audit fields.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();

        if (status == null) {
            status = NotificationStatus.RECEIVED;
        }

        if (priority == null) {
            priority = NotificationPriority.MEDIUM;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}