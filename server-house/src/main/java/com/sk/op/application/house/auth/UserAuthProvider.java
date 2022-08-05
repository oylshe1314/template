package com.sk.op.application.house.auth;

import com.sk.op.application.user.auth.UserAuthToken;
import com.sk.op.application.user.auth.UserDetails;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public abstract class UserAuthProvider implements AuthenticationProvider {

    protected abstract UserDetails doAuth(Authentication authRequest);

    @Override
    public Authentication authenticate(Authentication authRequest) throws AuthenticationException {
        UserDetails adminDetails = doAuth(authRequest);

        UserAuthToken authResult = new UserAuthToken(adminDetails);
        authResult.setDetails(authRequest.getDetails());
        return authResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UserAuthToken.class);
    }
}
