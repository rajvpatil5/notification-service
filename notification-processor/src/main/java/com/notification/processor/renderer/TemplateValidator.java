package com.notification.processor.renderer;

import com.notification.processor.models.entity.Template;

import java.util.Map;

public interface TemplateValidator {
    void validate(Template template,
                  Map<String, Object> payload);
}
