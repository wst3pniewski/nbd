package org.example.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedisCacheTest {
    private static RedisCache redisCache;

    @BeforeAll
    static void beforeAll() {
        redisCache = new RedisCache();
    }

    @Test
    void jsonSet() {
    }

    @Test
    void jsonGet() {
    }

    @Test
    void jsonDel() {
    }

    @Test
    void invalidateCache() {
        redisCache.invalidateCache();
    }
}