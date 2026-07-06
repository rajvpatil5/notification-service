package com.notification.api.services.impl;

import com.notification.api.dao.interfaces.TemplateDAO;
import com.notification.api.enums.TemplateStatus;
import com.notification.api.exception.ValidationException;
import com.notification.api.models.context.NotificationContext;
import com.notification.api.models.context.NotificationContextHolder;
import com.notification.api.models.entity.Template;
import com.notification.api.models.entity.TemplateVariable;
import com.notification.api.models.request.CreateUpdateTemplateRequest;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.request.TemplateVariableRequest;
import com.notification.api.models.response.FilterTemplateResponse;
import com.notification.api.models.response.TemplateResponse;
import com.notification.api.models.response.TemplateResponseDTO;
import com.notification.api.services.interfaces.TemplateService;
import com.notification.api.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static com.notification.api.constants.ErrorConstants.TEMPLATE_ALREADY_EXIST_ERROR;
import static com.notification.api.constants.ErrorConstants.TEMPLATE_NOT_EXIST_WITH_ID_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TemplateDAO templateDAO;
    @Override
    public TemplateResponse createTemplate(CreateUpdateTemplateRequest request) {
        Template template = new Template();
        template.setTenantId(UUID.fromString(CommonUtils.getCurrentTenantId()));

        template.setName(request.getName());
        template.setChannel(request.getChannel());
        template.setSubject(request.getSubject());
        template.setMessageTemplate(request.getMessageTemplate());

// Default values
        template.setStatus(TemplateStatus.ACTIVE);
        template.setVersion(1);

        updateTemplateVariables(template, request.getVariables());

        template.entityCreated();

        templateDAO.save(template);

        return new TemplateResponse(template);
    }

    @Override
    public FilterTemplateResponse findAllTemplate(TemplateFilterRequest filterRequest) {
        NotificationContextHolder.ignoreTenantIdInjection(true);

        Page<Template> data = null;
        try {
            data = templateDAO.filterTemplate(filterRequest.buildSearch(),
                    filterRequest.buildPageRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        data.stream().map(TemplateResponseDTO::new).toList();
        return new FilterTemplateResponse(data.toList(), data.hasNext(), data.getTotalElements());
    }

    @Override
    public TemplateResponse updateTemplate(String id, CreateUpdateTemplateRequest request) {
        Template template = new Template();

        if (CommonUtils.isNotEmpty(request.getName())) {

            templateDAO.findByTenantIdAndName(
                            CommonUtils.getCurrentTenantId(),
                            request.getName())
                    .ifPresent(existing -> {

                        if (!existing.getId().equals(template.getId())) {
                            throw new ValidationException(
                                    TEMPLATE_ALREADY_EXIST_ERROR,
                                    HttpStatus.BAD_REQUEST.value());
                        }
                    });

            template.setName(request.getName());
        }

        if (CommonUtils.isNotEmpty(request.getChannel())) {
            template.setChannel(request.getChannel());
        }

        if (CommonUtils.isNotEmpty(request.getSubject())) {
            template.setSubject(request.getSubject());
        }

        if (CommonUtils.isNotEmpty(request.getMessageTemplate())) {
            template.setMessageTemplate(request.getMessageTemplate());
        }

        if (CommonUtils.isNotEmpty(request.getVariables())) {
            updateTemplateVariables(template, request.getVariables());
        }

        template.entityCreated();

        templateDAO.save(template);

        return new TemplateResponse(template);
    }

    @Override
    public void updateTemplateVariables(
            Template template,
            List<TemplateVariableRequest> requests) {

        template.getVariables().clear();

        requests.forEach(request -> {

            TemplateVariable variable = new TemplateVariable();

            variable.setTemplate(template);
            variable.setVariableName(request.getVariableName());
            variable.setVariableType(request.getVariableType());
            variable.setRequired(request.getRequired());
            variable.setDescription(request.getDescription());
            variable.setDisplayOrder(request.getDisplayOrder());

            template.getVariables().add(variable);
        });
    }

    @Override
    public void deleteTemplate(String id, Supplier<? extends Throwable> exceptionHandler) {
        Template template = getTemplateForCurrentTenant(id);
        templateDAO.deleteByTemplateId(id, exceptionHandler);
    }

    private Template getTemplateForCurrentTenant(String id) {
        return templateDAO.findByTenantIdAndId(CommonUtils.getCurrentTenantId(), id)
                .orElseThrow(() -> new ValidationException(TEMPLATE_NOT_EXIST_WITH_ID_ERROR,
                        HttpStatus.BAD_REQUEST.value()));
    }
}


