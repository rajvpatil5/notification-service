package com.notification.api.dao.impl;

import com.notification.api.dao.interfaces.CacheService;
import com.notification.api.dao.interfaces.TemplateDAO;
import com.notification.api.dao.repositories.TemplateRepository;
import com.notification.api.models.entity.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static com.notification.api.utils.CommonUtils.getCurrentTenantId;
import static com.notification.api.utils.CommonUtils.isNotEmpty;

@Service
@Slf4j
@RequiredArgsConstructor
class TemplateDAOImpl implements TemplateDAO {
    private final TemplateRepository templateRepository;

    private final CacheService cacheService;

    @Override
    public Optional<Template> findByTenantIdAndName(final String tenantId, final String templateName) {
        return cacheService.getByName(tenantId, templateName, Template.class)
                .or(
                        () -> templateRepository.findByNameIgnoreCaseAndTenantId(templateName,
                                UUID.fromString(tenantId)).map(template -> {
                                    cacheService.putByName(tenantId, templateName, template);
                                    return template;
                                }
                        )
                );
    }

    @Override
    public Optional<Template> findByTenantIdAndId(String currentTenantId, String id) {
        return cacheService.getById(currentTenantId, id, Template.class).or(() -> {
            return templateRepository.findByIdAndTenantId(UUID.fromString(id), UUID.fromString(currentTenantId))
                    .map(template -> {
                        cacheService.putById(currentTenantId, id, template);
                        return template;
                    });
        });
    }

    @Override
    public Template save(Template template) {
        cacheService.putById(String.valueOf(template.getTenantId()), String.valueOf(template.getId()), template);
        cacheService.putByName(String.valueOf(template.getTenantId()), template.getName(), template);
        return templateRepository.save(template);
    }

    @Override
    public Page<Template> filterTemplate(Example<Template> example, Pageable pageable) {
        return templateRepository.findAll(example, pageable);
    }

    @Override
    public void deleteByTemplateId(String id, final Supplier<? extends Throwable> exceptionHandler) {
        findByTenantIdAndId(getCurrentTenantId(), id).ifPresentOrElse(template -> {
            cacheService.deleteById(String.valueOf(template.getTenantId()), String.valueOf(template.getId()));

            cacheService.deleteByName(String.valueOf(template.getTenantId()), template.getName());
            templateRepository.deleteById(UUID.fromString(id));
        }, () -> {
            if (isNotEmpty(exceptionHandler)) {
                exceptionHandler.get();
            }
        });
    }
}
