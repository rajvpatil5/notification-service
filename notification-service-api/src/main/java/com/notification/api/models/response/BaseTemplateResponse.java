package com.notification.api.models.response;

import com.notification.api.models.entity.Template;
import lombok.Data;

import java.util.List;

@Data
public class BaseTemplateResponse<I, R extends Number> {
    private List<Template> data;

    private Boolean hasMoreElement;

    private R totalCount;

}
