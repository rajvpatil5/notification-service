package com.notification.processor.dao.repositories;
 
import com.notification.processor.models.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemplateRepository extends JpaRepository<Template, UUID> {
    Optional<Template> findByNameIgnoreCaseAndTenantId(String name, UUID tenantId);

    Optional<Template> findByIdAndTenantId(UUID id, UUID tenantId);

}
