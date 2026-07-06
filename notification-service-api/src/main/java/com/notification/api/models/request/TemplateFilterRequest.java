package com.notification.api.models.request;

import com.notification.api.models.entity.Template;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TemplateFilterRequest extends BaseSearchDTO<Template> {
    private String name;

    @Override
    public Class<Template> getEntity() {
        return Template.class;
    }
}
