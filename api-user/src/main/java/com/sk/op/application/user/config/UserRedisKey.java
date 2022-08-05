package com.sk.op.application.user.config;

import com.sk.op.application.redis.config.RedisKey;

public enum UserRedisKey implements RedisKey {

    token;

    private final String module = RedisKey.super.module();

    @Override
    public String module() {
        return this.module;
    }
}
