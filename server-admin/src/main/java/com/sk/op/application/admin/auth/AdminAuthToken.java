package com.sk.op.application.admin.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AdminAuthToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final Object credentials;

    public AdminAuthToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    public AdminAuthToken(AdminDetails adminDetails) {
        super(adminDetails.getAuthorities());
        this.principal = adminDetails;
        this.credentials = adminDetails.getPassword();
        this.setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }
}
