package com.notification.api.pubsub.impl;

import com.notification.api.pubsub.interfaces.AwsSQSProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnProperty(value = "messaging.providers.aws.enabled", havingValue = "true")
class AwsSQSProviderImpl implements AwsSQSProvider {
    @Override
    public boolean sendNotification(String topic, String message) {
        log.info("Sending Notification using AWS SQS Provider for topic : {}", topic);
        return false;
    }
}
