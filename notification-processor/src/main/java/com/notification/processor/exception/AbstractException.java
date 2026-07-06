package com.notification.processor.exception;

public interface AbstractException {
    Integer getStatusCode();

    String getErrorMessage();
}
