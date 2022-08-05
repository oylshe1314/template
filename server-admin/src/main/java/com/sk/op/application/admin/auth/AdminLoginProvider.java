package com.sk.op.application.admin.auth;

import com.sk.op.application.admin.service.AuthService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AdminLoginProvider implements AuthenticationProvider {

    private final AuthService authService;

    public AdminLoginProvider(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Authentication authenticate(Authentication authRequest) throws AuthenticationException {
        String username = (String) authRequest.getPrincipal();
        String password = (String) authRequest.getCredentials();

        AdminDetails adminDetails = authService.login(username, password);

        AdminAuthToken authResult = new AdminAuthToken(adminDetails);
        authResult.setDetails(authRequest.getDetails());
        return authResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(AdminAuthToken.class);
    }
}
