package com.notification.api.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Map;

@Converter
public class JsonMapConverter implements AttributeConverter<Map<String, Object>, String> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {

        if (attribute == null || attribute.isEmpty()) {
            return null;
        }

        try {
            return OBJECT_MAPPER.writeValueAsString(attribute);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Unable to serialize payload.", ex);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {

        if (dbData == null || dbData.isBlank()) {
            return new HashMap<>();
        }

        try {
            return OBJECT_MAPPER.readValue(
                    dbData,
                    new TypeReference<Map<String, Object>>() {
                    }
            );
        } catch (Exception ex) {
            throw new IllegalArgumentException("Unable to deserialize payload.", ex);
        }
    }
}
