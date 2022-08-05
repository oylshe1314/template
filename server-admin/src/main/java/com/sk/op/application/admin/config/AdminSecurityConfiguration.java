package com.sk.op.application.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.op.application.admin.auth.AdminLoginProvider;
import com.sk.op.application.admin.auth.RoleApiAuthorityVoter;
import com.sk.op.application.admin.filter.AdminLoginFilter;
import com.sk.op.application.admin.handler.AdminAccessDeniedHandler;
import com.sk.op.application.admin.handler.AdminLoginHandler;
import com.sk.op.application.admin.handler.AdminLogoutHandler;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@EnableWebSecurity
@SpringBootConfiguration
public class AdminSecurityConfiguration {

    private final ObjectMapper objectMapper;
    private final AdminLoginProvider adminLoginProvider;
    private final AdminLoginHandler adminLoginHandler;
    private final AdminLogoutHandler adminLogoutHandler;
    private final AdminAccessDeniedHandler adminAccessDeniedHandler;

    public AdminSecurityConfiguration(ObjectMapper objectMapper, AdminLoginProvider adminLoginProvider, AdminAccessDeniedHandler adminAccessDeniedHandler, AdminLoginHandler adminLoginHandler, AdminLogoutHandler adminLogoutHandler) {
        this.objectMapper = objectMapper;
        this.adminLoginProvider = adminLoginProvider;
        this.adminAccessDeniedHandler = adminAccessDeniedHandler;
        this.adminLoginHandler = adminLoginHandler;
        this.adminLogoutHandler = adminLogoutHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors();//.disable();
        httpSecurity.csrf().disable();
//        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.httpBasic().authenticationEntryPoint(adminAccessDeniedHandler);
        httpSecurity.exceptionHandling().accessDeniedHandler(adminAccessDeniedHandler);
        httpSecurity.authorizeRequests().antMatchers("/doc/**", "/v3/api-docs/**").permitAll().anyRequest().authenticated().accessDecisionManager(accessDecisionManager());
        httpSecurity.logout().logoutUrl("/auth/logout").addLogoutHandler(adminLogoutHandler).logoutSuccessHandler(adminLogoutHandler);

        httpSecurity.addFilterAt(adminLoginFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    private AdminLoginFilter adminLoginFilter() {
        AdminLoginFilter adminLoginFilter = new AdminLoginFilter("/auth/login", objectMapper);
        adminLoginFilter.setAuthenticationManager(new ProviderManager(adminLoginProvider));
        adminLoginFilter.setAuthenticationSuccessHandler(adminLoginHandler);
        adminLoginFilter.setAuthenticationFailureHandler(adminLoginHandler);
        return adminLoginFilter;
    }

    private AccessDecisionManager accessDecisionManager() {
        return new UnanimousBased(Arrays.asList(new WebExpressionVoter(), new RoleApiAuthorityVoter()));
    }
}
