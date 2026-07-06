package com.notification.api.models.request;

import com.notification.api.enums.SortType;
import lombok.Data;

@Data
public class SortRequest {
    private String sortKey;

    private SortType sortType;
}
