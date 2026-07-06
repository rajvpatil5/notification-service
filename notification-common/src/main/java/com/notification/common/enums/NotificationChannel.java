package com.notification.common.enums;

import lombok.Getter;

@Getter
public enum NotificationChannel {
    SMS("SMS"),
    EMAIL("EMAIL"),
    PUSH("PUSH"),
    WEBHOOK("WEBHOOK"),
    WHATSAPP("WHATSAPP");

    private final String value;

    NotificationChannel(String value) {
        this.value = value;
    }

}
