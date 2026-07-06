package com.notification.processor.models.context;

public record NotificationContext(String tenantId,

                                  boolean ignoreTenantIdInjection) {

}
