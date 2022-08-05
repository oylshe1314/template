package com.sk.op.application.admin.auth;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

public class RoleApiAuthorityVoter  implements AccessDecisionVoter<FilterInvocation> {

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation invocation, Collection<ConfigAttribute> attributes) {
        if (!(authentication instanceof AdminAuthToken)) {
            return ACCESS_ABSTAIN;
        }

        for (ConfigAttribute attribute : attributes) {
            if("permitAll".equals(attribute.toString())) {
                return ACCESS_GRANTED;
            }
        }

        String reqPath = invocation.getHttpRequest().getServletPath();
        if (reqPath.startsWith("/common/")) {
            return ACCESS_GRANTED;
        }

        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            String apiPath = grantedAuthority.getAuthority();
            if (apiPath.endsWith("/**")) {
                if (reqPath.startsWith(apiPath.substring(0, apiPath.length() - 3))) {
                    return ACCESS_GRANTED;
                }
            } else {
                if (apiPath.equalsIgnoreCase(reqPath)) {
                    return ACCESS_GRANTED;
                }
            }
        }

        return ACCESS_DENIED;
    }
}
