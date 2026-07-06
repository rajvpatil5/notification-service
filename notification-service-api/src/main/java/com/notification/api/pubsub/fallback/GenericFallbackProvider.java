package com.notification.api.pubsub.fallback;

public interface GenericFallbackProvider {
    boolean sendNotification(String topic, String message);
}
