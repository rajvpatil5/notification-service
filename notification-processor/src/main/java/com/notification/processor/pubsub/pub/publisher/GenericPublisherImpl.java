package com.notification.processor.pubsub.pub.publisher;

import com.notification.processor.config.ApplicationProperties;
import com.notification.processor.exception.ValidationException;
import com.notification.processor.pubsub.pub.fallback.GenericFallbackProvider;
import com.notification.processor.pubsub.pub.primary.GenericProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@RequiredArgsConstructor
class GenericPublisherImpl implements GenericPublisher {
    private final List<GenericProvider> genericProviders;

    private final List<GenericFallbackProvider> genericFallbackProviders;

    private ObjectMapper mapper;

    private ApplicationProperties applicationProperties;

    @Override
    public void sendNotification(String topic, String message) {

        log.info("Sending notification using generic publisher");
        AtomicBoolean success = new AtomicBoolean(false);

        genericProviders.forEach(provider -> {
            boolean providerStatus = provider.sendNotification(topic, message);
            if (!success.get()) success.set(providerStatus);

            if (providerStatus) {
                log.info("Notification send topic = {} using provider = {}", topic,
                        provider.getClass().getSimpleName());
            } else {
                log.error("Error while publishing data for topic = {} using provider = {}", topic,
                        provider.getClass().getSimpleName());
            }

        });

        genericFallbackProviders.forEach(fallback -> {
            if (!success.get()) {
                boolean fallbackProviderStatus = fallback.sendNotification(topic, message);
                if (fallbackProviderStatus) {
                    success.set(true);
                    log.info("Notification send to topic = {} using fallback provider = {}", topic,
                            fallback.getClass().getSimpleName());
                } else {
                    log.error("Error while publishing data to topic = {} using fallback provider = {}", topic,
                            fallback.getClass().getSimpleName());
                }
            }
        });
    }

    @Override
    public void sendDataToIngest(Object input) {
        sendNotification(applicationProperties.getIngestTopic(), convertDataIntoString(input));
    }

    @Override
    public void sendDataToAudit(Object input) {
        sendNotification(applicationProperties.getAuditTopic(), convertDataIntoString(input));

    }

    private String convertDataIntoString(final Object input) {
        try {
            return mapper.writeValueAsString(input);
        } catch (Exception e) {
            throw new ValidationException("Error while parsing input payload",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
