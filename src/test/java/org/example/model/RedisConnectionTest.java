package org.example.model;

import org.junit.jupiter.api.Test;

public class RedisConnectionTest {

    @Test
    public void testRedisConnection() {
        try(RedisConnection redisConnection = new RedisConnection()) {
            redisConnection.testConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
