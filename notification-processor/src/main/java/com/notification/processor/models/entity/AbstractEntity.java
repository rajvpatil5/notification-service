package com.notification.processor.models.entity;

import com.notification.processor.utils.CommonUtils;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class AbstractEntity {
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime  updatedAt;

    public void entityCreated() {
        setCreatedAt(CommonUtils.getCurrentTimeStamp());
        setUpdatedAt(CommonUtils.getCurrentTimeStamp());
    }
}
