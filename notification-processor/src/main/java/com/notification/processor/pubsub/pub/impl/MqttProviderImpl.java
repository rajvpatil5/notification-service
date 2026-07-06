package com.notification.processor.pubsub.pub.impl;

import com.notification.processor.pubsub.pub.interfaces.MqttProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@ConditionalOnProperty(value = "messaging.fallback.mqtt.enabled", havingValue = "true")
@Service
class MqttProviderImpl implements MqttProvider {
    @Override
    public boolean sendNotification(String topic, String message) {
        log.info("Sending notification using mqtt for topic : {}", topic);
        return false;
    }
}
