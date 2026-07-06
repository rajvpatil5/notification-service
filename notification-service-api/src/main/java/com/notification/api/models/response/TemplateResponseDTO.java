package com.notification.api.models.response;

import com.notification.api.models.entity.Template;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateResponseDTO {
    private Map<String, String> templateVariable;

    private String name;

    private String id;

    public TemplateResponseDTO(final Template template) {
        BeanUtils.copyProperties(template, this);
        setId(template.getId().toString());
    }

    public TemplateResponseDTO(TemplateResponseDTO templateResponseDTO) {
    }
}
