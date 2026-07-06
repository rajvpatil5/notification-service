package com.notification.processor.pubsub.pub.primary;

public interface GenericProvider {
    boolean sendNotification(String topic, String message);
}
