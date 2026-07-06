package com.notification.processor.dao.interfaces;

import com.notification.processor.models.entity.Template;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.function.Supplier;

public interface TemplateDAO {
    Optional<Template> findByTenantIdAndName(String tenantId, String templateName);

    Template save(Template template);

    Page<Template> filterTemplate(final Example<Template> example, final Pageable pageable);

    Optional<Template> findByTenantIdAndId(String currentTenantId, String id);

    void deleteByTemplateId(String id, Supplier<? extends Throwable> exceptionHandler);
}
