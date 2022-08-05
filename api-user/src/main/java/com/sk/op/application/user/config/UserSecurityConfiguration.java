package com.sk.op.application.user.config;

import com.sk.op.application.user.handler.UserAccessDeniedHandler;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@SpringBootConfiguration
@Setter(onMethod_ = @Autowired)
@EnableAutoConfiguration(exclude = {UserDetailsServiceAutoConfiguration.class})
public class UserSecurityConfiguration {

    private UserAccessDeniedHandler userAccessDeniedHandler;

    protected void httpSecurityConfigure(HttpSecurity httpSecurity) throws Exception {

    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors().disable();
        httpSecurity.csrf().disable();
//        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.httpBasic().authenticationEntryPoint(userAccessDeniedHandler);
        httpSecurity.exceptionHandling().accessDeniedHandler(userAccessDeniedHandler);
        httpSecurity.authorizeRequests().antMatchers("/doc/**", "/v3/api-docs/**").permitAll().anyRequest().authenticated();

        httpSecurityConfigure(httpSecurity);

        return httpSecurity.build();
    }
}
