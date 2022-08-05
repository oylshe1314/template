package com.sk.op.application.admin.auth;

import org.springframework.security.core.GrantedAuthority;

public class RoleApiAuthority implements GrantedAuthority {

    private final String path;

    public RoleApiAuthority(String path) {
        this.path = path;
    }

    @Override
    public String getAuthority() {
        return this.path;
    }
}
