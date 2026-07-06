package com.notification.api.models.request;

import com.notification.api.enums.VariableType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateVariableRequest {

    private String variableName;

    private VariableType variableType;

    private Boolean required;

    private String description;

    private Integer displayOrder;
}
