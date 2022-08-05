package com.sk.op.application.user.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = TokenProperties.PREFIX)
public class TokenProperties {

    protected static final String PREFIX = "op.user.token";

    private String key;

    private long expiration;
}
