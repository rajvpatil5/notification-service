package com.notification.processor.pubsub.pub.publisher;

public interface GenericPublisher {
    void sendNotification(String topic, String message);

    void sendDataToIngest(Object input);

    void sendDataToAudit(Object input);

}
