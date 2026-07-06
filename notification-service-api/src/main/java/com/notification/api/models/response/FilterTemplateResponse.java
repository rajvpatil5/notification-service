package com.notification.api.models.response;

import com.notification.api.models.entity.Template;
import lombok.Data;

import java.util.List;

@Data
public class FilterTemplateResponse extends BaseTemplateResponse<TemplateResponseDTO, Long> {
    public FilterTemplateResponse(List<Template> list, boolean hasMoreElement, Long totalCount) {
        setData(list);
        setHasMoreElement(hasMoreElement);
        setTotalCount(totalCount);
    }
}
