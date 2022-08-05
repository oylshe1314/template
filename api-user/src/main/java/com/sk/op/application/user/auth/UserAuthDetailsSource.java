package com.sk.op.application.user.auth;

import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

public class UserAuthDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, UserAuthDetails> {

    @Override
    public UserAuthDetails buildDetails(HttpServletRequest context) {
        return new UserAuthDetails(context);
    }
}
