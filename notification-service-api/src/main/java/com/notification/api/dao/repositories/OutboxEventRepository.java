package com.notification.api.dao.repositories;

import com.notification.api.enums.OutboxStatus;
import com.notification.api.models.entity.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {

    /**
     * Fetch all events with the given status.
     */
    List<OutboxEvent> findByStatus(OutboxStatus status);

    List<OutboxEvent> findTop100ByStatusOrderByCreatedAt(OutboxStatus status);
}