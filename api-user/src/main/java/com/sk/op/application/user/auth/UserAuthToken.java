package com.sk.op.application.user.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserAuthToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final Object credentials;

    public UserAuthToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    public UserAuthToken(UserDetails userDetails) {
        super(userDetails.getAuthorities());
        this.principal = userDetails;
        this.credentials = userDetails.getPassword();
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

    @Override
    public String getName() {
        if (this.principal instanceof String) {
            return (String) this.principal;
        }
        if (this.principal instanceof UserDetails) {
            return ((UserDetails) this.principal).getUsername();
        }
        return super.getName();
    }
}
