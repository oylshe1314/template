package com.sk.op.application.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.op.application.common.dto.ResponseDto;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class CustomRestHandler {

    private final ObjectMapper objectMapper;

    protected CustomRestHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected void respond(HttpServletResponse response, ResponseDto<?> responseDto) throws IOException {
        response.setStatus(200);
        response.addHeader("Content-Type", "application/json");
        objectMapper.writeValue(response.getOutputStream(), responseDto);
    }

    protected void respond(HttpServletResponse response, ResponseDto<?> responseDto, Map<String, String> headers) throws IOException {
        response.setStatus(200);
        response.addHeader("Content-Type", "application/json");
        headers.forEach(response::addHeader);
        objectMapper.writeValue(response.getOutputStream(), responseDto);
    }

    protected void respondError(HttpServletResponse response, HttpStatus httpStatus) throws IOException {
        response.sendError(httpStatus.value());
    }

    protected void respondException(HttpServletResponse response, Exception exception) throws IOException {
        respond(response, ResponseDto.fail(exception));
    }
}
