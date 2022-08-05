package com.sk.op.application.user.component;

import com.sk.op.application.user.auth.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserStore {

    private static final Map<Long, UserDetails> USER_DETAILS_MAP = new ConcurrentHashMap<>();

    public UserDetails get(Long userId) {
        return USER_DETAILS_MAP.get(userId);
    }

    public UserDetails set(Long userId, long expiration) {
        return USER_DETAILS_MAP.get(userId);
    }

    private static class Entity {
        private final Long userId;
        private final Date expiration;
        private final UserDetails userDetails;

        public Entity(Long userId, Date expiration, UserDetails userDetails) {
            this.userId = userId;
            this.expiration = expiration;
            this.userDetails = userDetails;
        }
    }
}
