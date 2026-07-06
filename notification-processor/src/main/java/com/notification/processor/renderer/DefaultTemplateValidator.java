package com.notification.processor.renderer;

import com.notification.processor.exception.ValidationException;
import com.notification.processor.models.entity.Template;
import com.notification.processor.models.entity.TemplateVariable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DefaultTemplateValidator implements TemplateValidator {

    @Override
    public void validate(Template template,
                         Map<String, Object> payload) {

        if (template == null) {
            throw new ValidationException("Template cannot be null.");
        }

        if (template.getVariables() == null
                || template.getVariables().isEmpty()) {
            return;
        }

        for (TemplateVariable variable : template.getVariables()) {

            if (!Boolean.TRUE.equals(variable.getRequired())) {
                continue;
            }

            Object value = payload.get(variable.getVariableName());

            if (value == null) {
                throw new ValidationException(
                        "Required variable '" +
                                variable.getVariableName() +
                                "' is missing.");
            }

            if (value instanceof String
                    && ((String) value).isBlank()) {

                throw new ValidationException(
                        "Required variable '" +
                                variable.getVariableName() +
                                "' is blank.");
            }
        }
    }
}