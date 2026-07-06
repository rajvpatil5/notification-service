package com.notification.api.models.request;

import com.notification.common.enums.NotificationChannel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CreateUpdateTemplateRequest {

    @NotBlank(message = "Message required")
    private String messageTemplate;

    @NotBlank(message = "Name required")
    private String name;

    private NotificationChannel channel;

    private String subject;

    private List<TemplateVariableRequest> variables;
}
