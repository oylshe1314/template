package com.sk.op.application.admin.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.op.application.admin.auth.AdminDetails;
import com.sk.op.application.admin.dto.LoginResultDto;
import com.sk.op.application.common.dto.ResponseDto;
import com.sk.op.application.common.handler.CustomRestHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AdminLoginHandler extends CustomRestHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    public AdminLoginHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        respond(response, ResponseDto.succeed(new LoginResultDto((AdminDetails) authentication.getPrincipal())));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        respondException(response, exception);
    }
}