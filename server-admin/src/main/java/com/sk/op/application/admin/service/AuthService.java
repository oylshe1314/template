package com.sk.op.application.admin.service;

import com.sk.op.application.admin.auth.AdminDetails;
import org.springframework.security.core.AuthenticationException;

public interface AuthService {

    AdminDetails login(String username, String password) throws AuthenticationException;
}
