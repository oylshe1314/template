package com.sk.op.application.user.auth;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class UserAuthDetails extends WebAuthenticationDetails {

    public UserAuthDetails(HttpServletRequest request) {
        super(request);
    }
}
