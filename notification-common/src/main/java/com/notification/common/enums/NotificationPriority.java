package com.notification.common.enums;

import lombok.Getter;

@Getter
public enum NotificationPriority {
    HIGH("HIGH"),
    MEDIUM("MEDIUM"),
    LOW("LOW");

    private final String value;

    NotificationPriority(String value) {
        this.value = value;
    }

}
