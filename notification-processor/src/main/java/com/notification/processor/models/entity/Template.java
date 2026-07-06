package com.notification.processor.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.notification.common.enums.NotificationChannel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "TEMPLATE")
@EqualsAndHashCode(callSuper = true)
public class Template extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "TENANT_ID", nullable = false)
    private UUID tenantId;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "CHANNEL", nullable = false, length = 30)
    private NotificationChannel channel;

    @Column(name = "SUBJECT", length = 500)
    private String subject;

    @Lob
    @Column(name = "MESSAGE_TEMPLATE", nullable = false)
    private String messageTemplate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 30)
    private TemplateStatus status;

    @Column(name = "VERSION", nullable = false)
    private Integer version;

    @OneToMany(
            mappedBy = "template",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    @ToString.Exclude
    private List<TemplateVariable> variables = new ArrayList<>();
}