package com.notification.api.utils;

import com.notification.api.models.context.NotificationContextHolder;
import org.slf4j.MDC;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;

import static com.notification.common.constants.ApplicationConstant.X_REQUEST_ID;

public class CommonUtils {
    private static final Calendar calendar = Calendar.getInstance();

    public static boolean isNotEmpty(final Object input) {
        return !ObjectUtils.isEmpty(input);
    }

    public static boolean isEmpty(final Object input) {
        return ObjectUtils.isEmpty(input);
    }

    public static LocalDateTime getCurrentTimeStamp() {
        return LocalDateTime.now();
    }

    public static UUID generateUUID() {
        return UUID.randomUUID();
    }

    public static String getCurrentTenantId() {
        return NotificationContextHolder.getContext().tenantId();
    }

    public static String getCurrentTraceId() {
        return MDC.get(X_REQUEST_ID);
    }
}
