package com.sk.op.application.house.auth;

import com.sk.op.application.house.service.AuthService;
import com.sk.op.application.user.auth.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserWechatAuthProvider extends UserAuthProvider {

    protected final AuthService authService;

    public UserWechatAuthProvider(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected UserDetails doAuth(Authentication authRequest) {
        String code = (String) authRequest.getPrincipal();

        return authService.byWechat(code);
    }
}
