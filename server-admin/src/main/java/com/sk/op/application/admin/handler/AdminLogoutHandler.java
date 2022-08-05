package com.sk.op.application.admin.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.op.application.common.dto.ResponseDto;
import com.sk.op.application.common.handler.CustomRestHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AdminLogoutHandler extends CustomRestHandler implements LogoutHandler, LogoutSuccessHandler {

    public AdminLogoutHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        respond(response, ResponseDto.succeed());
    }
}
