package com.notification.processor.renderer;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DefaultTemplateRenderer implements TemplateRenderer {

    @Override
    public String render(String template, Map<String, Object> payload) {
        if (template == null) {
            return "";
        }

        String renderedTemplate = template;
        if (payload == null || payload.isEmpty()) {
            return renderedTemplate;
        }

        for (Map.Entry<String, Object> entry : payload.entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}";
            String value = entry.getValue() == null ? "" : String.valueOf(entry.getValue());
            renderedTemplate = renderedTemplate.replace(placeholder, value);
        }
        return renderedTemplate;
    }
}
