package com.notification.api.services.interfaces;

import com.notification.api.models.entity.Template;
import com.notification.api.models.request.CreateUpdateTemplateRequest;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.request.TemplateVariableRequest;
import com.notification.api.models.response.FilterTemplateResponse;
import com.notification.api.models.response.TemplateResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.function.Supplier;

public interface TemplateService {
    TemplateResponse createTemplate(CreateUpdateTemplateRequest request);

    FilterTemplateResponse findAllTemplate(TemplateFilterRequest filterRequest);

    TemplateResponse updateTemplate(String id, @Valid CreateUpdateTemplateRequest request);

    void updateTemplateVariables(
            Template template,
            List<TemplateVariableRequest> requests);

    void deleteTemplate(String id, Supplier<? extends Throwable> exceptionHandler);
}
