package com.sk.op.application.admin.filter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.op.application.admin.auth.AdminAuthToken;
import com.sk.op.application.admin.dto.LoginDto;
import com.sk.op.application.common.dto.ResponseStatus;
import com.sk.op.application.common.exception.StandardRespondException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public AdminLoginFilter(String processesUrl, ObjectMapper objectMapper) {
        super(processesUrl);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        try {
            LoginDto loginDto = this.objectMapper.readValue(request.getInputStream(), LoginDto.class);

            if (!StringUtils.hasText(loginDto.getUsername()) || !StringUtils.hasText(loginDto.getPassword())) {
                throw new BadCredentialsException("", new StandardRespondException(ResponseStatus.PARAMETER_ERROR, "用户名或者密码不能为空"));
            }

            AdminAuthToken authRequest = new AdminAuthToken(loginDto.getUsername(), loginDto.getPassword());
            authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (JacksonException e) {
            throw new BadCredentialsException("", new StandardRespondException(ResponseStatus.PARAMETER_ERROR, "格式不正确，请使用JSON格式"));
        }
    }
}
