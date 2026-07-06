package com.notification.processor.models.entity;

import lombok.Getter;

@Getter
public enum NotificationType {
    SMS("SMS"),
    EMAIL("EMAIL"),
    WEBHOOK("WEBHOOK");

    private final String value;

    NotificationType(String value) {
        this.value = value;
    }

}
