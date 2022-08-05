package com.sk.op.application.house.auth;

import com.sk.op.application.house.service.AuthService;
import com.sk.op.application.user.auth.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordAuthProvider extends UserAuthProvider {

    protected final AuthService authService;

    public UserPasswordAuthProvider(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected UserDetails doAuth(Authentication authRequest) {
        String account = (String) authRequest.getPrincipal();
        String password = (String) authRequest.getCredentials();

        return authService.byPassword(account, password);
    }
}
