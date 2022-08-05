package com.sk.op.application.house.filter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.op.application.common.dto.ResponseStatus;
import com.sk.op.application.common.exception.StandardRespondException;
import com.sk.op.application.house.dto.LoginDto;
import com.sk.op.application.user.auth.UserAuthToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserSmsAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public UserSmsAuthFilter(String processesUrl, ObjectMapper objectMapper) {
        super(processesUrl);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        try {
            LoginDto loginDto = this.objectMapper.readValue(request.getInputStream(), LoginDto.class);

            if (!StringUtils.hasText(loginDto.getAccount()) || !StringUtils.hasText(loginDto.getSmsCode())) {
                throw new BadCredentialsException("", new StandardRespondException(ResponseStatus.PARAMETER_ERROR, "手机号或者验证码不能为空"));
            }

            UserAuthToken authRequest = new UserAuthToken(loginDto.getAccount(), loginDto.getSmsCode());
            authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (JacksonException e) {
            throw new BadCredentialsException("", new StandardRespondException(ResponseStatus.PARAMETER_ERROR, "参数格式不正确，请使用JSON格式"));
        }
    }
}
