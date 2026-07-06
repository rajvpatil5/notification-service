package com.notification.processor.pubsub.pub.fallback;

public interface GenericFallbackProvider {
    boolean sendNotification(String topic, String message);
}
