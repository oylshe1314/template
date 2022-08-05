package com.sk.op.application.house.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.op.application.house.auth.UserPasswordAuthProvider;
import com.sk.op.application.house.auth.UserSmsAuthProvider;
import com.sk.op.application.house.auth.UserWechatAuthProvider;
import com.sk.op.application.house.filter.UserPasswordAuthFilter;
import com.sk.op.application.house.filter.UserSmsAuthFilter;
import com.sk.op.application.house.filter.UserWechatAuthFilter;
import com.sk.op.application.house.handler.UserLoginHandler;
import com.sk.op.application.house.handler.UserLogoutHandler;
import com.sk.op.application.user.config.UserSecurityConfiguration;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@SpringBootConfiguration
@Setter(onMethod_ = @Autowired)
public class HouseUserSecurityConfiguration extends UserSecurityConfiguration {

    private ObjectMapper objectMapper;
    private UserLoginHandler loginHandler;
    private UserLogoutHandler logoutHandler;
    private UserPasswordAuthProvider userPasswordAuthProvider;
    private UserSmsAuthProvider userSmsAuthProvider;
    private UserWechatAuthProvider userWechatAuthProvider;

    @Override
    protected void httpSecurityConfigure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.logout().logoutUrl("/auth/logout").addLogoutHandler(logoutHandler).logoutSuccessHandler(logoutHandler);

        httpSecurity.addFilterAt(userPasswordAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAt(userSmsAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAt(userWechatAuthFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private UserPasswordAuthFilter userPasswordAuthFilter() {
        UserPasswordAuthFilter userPasswordAuthFilter = new UserPasswordAuthFilter("/auth/login/password", objectMapper);
        userPasswordAuthFilter.setAuthenticationManager(new ProviderManager(userPasswordAuthProvider));
        userPasswordAuthFilter.setAuthenticationSuccessHandler(loginHandler);
        userPasswordAuthFilter.setAuthenticationFailureHandler(loginHandler);
        return userPasswordAuthFilter;
    }

    private UserSmsAuthFilter userSmsAuthFilter() {
        UserSmsAuthFilter userPasswordAuthFilter = new UserSmsAuthFilter("/auth/login/sms", objectMapper);
        userPasswordAuthFilter.setAuthenticationManager(new ProviderManager(userSmsAuthProvider));
        userPasswordAuthFilter.setAuthenticationSuccessHandler(loginHandler);
        userPasswordAuthFilter.setAuthenticationFailureHandler(loginHandler);
        return userPasswordAuthFilter;
    }

    private UserWechatAuthFilter userWechatAuthFilter() {
        UserWechatAuthFilter userPasswordAuthFilter = new UserWechatAuthFilter("/auth/login/wechat", objectMapper);
        userPasswordAuthFilter.setAuthenticationManager(new ProviderManager(userWechatAuthProvider));
        userPasswordAuthFilter.setAuthenticationSuccessHandler(loginHandler);
        userPasswordAuthFilter.setAuthenticationFailureHandler(loginHandler);
        return userPasswordAuthFilter;
    }
}
