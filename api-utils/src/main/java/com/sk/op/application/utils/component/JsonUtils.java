package com.sk.op.application.utils.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;

@Slf4j
@Component
public class JsonUtils {

    private final ObjectMapper objectMapper;

    public JsonUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T fromJson(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            log.error("Read value from json error", e);
            return null;
        }
    }

    public <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("Read value from json error", e);
            return null;
        }
    }

    public <T> String toJson(T value) {
        try {
            StringWriter writer = new StringWriter();
            objectMapper.writeValue(writer, value);
            return writer.toString();
        } catch (IOException e) {
            log.error("Read value from json error", e);
            return null;
        }
    }
}
