package com.sk.op.application.user.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.op.application.common.dto.ResponseStatus;
import com.sk.op.application.common.exception.StandardRespondException;
import com.sk.op.application.common.handler.CustomRestHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

@Component
public class UserAccessDeniedHandler extends CustomRestHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    public UserAccessDeniedHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void commence(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        respondException(response, new StandardRespondException(ResponseStatus.NOT_LOGGED));
    }

    @Override
    public void handle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        respondException(response, new StandardRespondException(ResponseStatus.ACCESS_DENIED));
    }
}
