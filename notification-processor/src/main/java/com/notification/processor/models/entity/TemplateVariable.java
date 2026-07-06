package com.notification.processor.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@Entity
@Table(name = "TEMPLATE_VARIABLE")
public class TemplateVariable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMPLATE_ID", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private Template template;

    @Column(name = "VARIABLE_NAME", nullable = false, length = 100)
    private String variableName;

    @Enumerated(EnumType.STRING)
    @Column(name = "VARIABLE_TYPE", nullable = false, length = 30)
    private VariableType variableType;

    @Column(name = "REQUIRED", nullable = false)
    private Boolean required;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;
}
