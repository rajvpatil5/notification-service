package com.notification.api.pubsub.impl;

import com.notification.api.pubsub.interfaces.RabbitMqProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(value = "messaging.fallback.rabbitmq.enabled", havingValue = "true")
class RabbitMqProviderImpl implements RabbitMqProvider {
    @Override
    public boolean sendNotification(String topic, String message) {
        log.info("RabbitMqProviderImpl::sendNotification -> topic = {}, message = {}", topic, message);
        return false;
    }
}
