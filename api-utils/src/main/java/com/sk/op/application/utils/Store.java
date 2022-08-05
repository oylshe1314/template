package com.sk.op.application.utils;

import lombok.Getter;

import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Store<T> {

    private final Map<Long, Entity<T>> store = new ConcurrentHashMap<>();
    private final Queue<Entity<T>> queue = new LinkedBlockingQueue<>();

    public Store() {
        new Thread(() -> {
            for(;;) {
                Entity<T> entity = queue.peek();
                if(entity == null) {
                    continue;
                }
                if (!entity.expiration.before(new Date())) {
                    queue.remove();
                }
            }
        }).start();
    }

    public T get(Long userId) {
        return Entity.data(store.get(userId));
    }

    public void set(Long userId, T value, long expiration) {
        Entity<T> entity = new Entity<>(userId, value, new Date(System.currentTimeMillis() + expiration * 1000));
        store.put(entity.getUserId(), entity);

        queue.add(entity);
    }

    @Getter
    private record Entity<T>(Long userId, T value, Date expiration) {
        private static <T> T data(Entity<T> entity) {
            return entity == null ? null : entity.value;
        }
    }
}
