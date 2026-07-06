package com.notification.processor.constants;

public interface ErrorConstants {
    String TEMPLATE_ALREADY_EXIST_ERROR = "Template already exist with given name.";

    String TEMPLATE_NOT_EXIST_WITH_ID_ERROR = "Template doesn't exist with given id.";

    String TEMPLATE_ID_IS_REQUIRED = "Template id is required.";

    String KAFKA_EVENT_PARSER_ERROR = "Error while parsing kafka packets.";
}
