package com.notification.processor.renderer;

import java.util.Map;
import java.util.Objects;

public interface TemplateRenderer {
    String render(String template, Map<String, Object>payload);
}
