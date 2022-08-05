package com.sk.op.application.house.auth;

import com.sk.op.application.house.service.AuthService;
import com.sk.op.application.user.auth.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserSmsAuthProvider extends UserAuthProvider {

    protected final AuthService authService;

    public UserSmsAuthProvider(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected UserDetails doAuth(Authentication authRequest) {
        String account = (String) authRequest.getPrincipal();
        String smsCode = (String) authRequest.getCredentials();

        return authService.bySms(account, smsCode);
    }
}
