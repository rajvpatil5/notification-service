package com.notification.api.models.request;

import com.notification.common.dto.Recipient;
import com.notification.common.enums.NotificationChannel;
import com.notification.common.enums.NotificationPriority;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationRequest {

    /**
     * Business reference. Example: ORDER-10001
     */
    private String referenceId;

    @NotBlank
    private String templateId;

    @NotNull
    private NotificationChannel notificationChannel;

    @Builder.Default
    private NotificationPriority priority = NotificationPriority.MEDIUM;

    @Valid
    @NotNull
    private Recipient recipient;

    @NotNull
    private Map<String, Object> payload;

    /**
     * Null = send immediately
     */
    private LocalDateTime scheduledAt;

}