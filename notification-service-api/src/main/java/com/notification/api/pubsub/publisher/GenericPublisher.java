package com.notification.api.pubsub.publisher;

public interface GenericPublisher {
    void sendNotification(String topic, String message);

    void sendDataToIngest(Object input);

    void sendDataToAudit(Object input);

    void sendJsonToIngest(String json);
}
