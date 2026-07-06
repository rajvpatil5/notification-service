package com.notification.common.event;

import com.notification.common.dto.Recipient;
import com.notification.common.enums.NotificationChannel;
import com.notification.common.enums.NotificationPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationCreatedEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String tenantId;

    /**
     * Unique notification identifier.
     */
    private UUID notificationId;

    /**
     * Business reference (optional). Example: ORDER-1001, PAYMENT-9087
     */
    private String referenceId;

    /**
     * Notification template code.
     */
    private String templateId;

    /**
     * EMAIL / SMS / PUSH
     */
    private NotificationChannel channel;

    /**
     * Recipient.
     */
    private Recipient recipient;

    /**
     * HIGH / MEDIUM / LOW
     */
    private NotificationPriority priority;

    /**
     * Variables required for template rendering.
     */
    private Map<String, Object> payload;

    /**
     * If null -> send immediately.
     */
    private LocalDateTime scheduledAt;

    /**
     * Event creation timestamp.
     */
    private LocalDateTime createdAt;
}
/*
* | Field            | Why?                                        |
| ---------------- | ------------------------------------------- |
| `notificationId` | Unique identifier across the system         |
| `referenceId`    | Business correlation (order, payment, etc.) |
| `templateCode`   | Processor loads the template                |
| `channel`        | Processor selects Email/SMS/Push sender     |
| `recipient`      | Destination                                 |
| `priority`       | Used later for prioritization               |
| `payload`        | Template variables                          |
| `scheduledAt`    | Used later for scheduling                   |
| `createdAt`      | Auditing and debugging                      |

* */