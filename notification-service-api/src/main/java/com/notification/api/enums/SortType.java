package com.notification.api.enums;

import lombok.Getter;

@Getter
public enum SortType {
    ASC("ASC"),
    DESC("DESC");

    private final String value;

    SortType(String value) {
        this.value = value;
    }

}
