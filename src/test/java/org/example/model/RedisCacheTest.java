package org.example.model;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.example.model.clients.Client;
import org.example.model.mappers.ClientRedisMapper;
import org.example.model.redis.ClientRedis;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RedisCacheTest {
    private static RedisCache redisCache;
    private final static Jsonb jsonb = JsonbBuilder.create();

    private Client client;

    @BeforeAll
    static void beforeAll() {
        redisCache = new RedisCache();
    }

    @BeforeEach
    void beforeEach() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, "Street",
                "City", "42");
    }

    @Test
    void jsonSetAndGet() {
        if (redisCache.isConnected()) {
            String json = jsonb.toJson(ClientRedisMapper.toRedis(client));
            String key = "client:" + client.getId().toString();
            redisCache.jsonSet(key, json);
            Object obj = redisCache.jsonGet("client:" + client.getId().toString());
            json = jsonb.toJson(obj);
            Client client1 = ClientRedisMapper.fromRedis(jsonb.fromJson(json, ClientRedis.class));
            assertEquals(client.getId(), client1.getId());
        }
    }

    @Test
    void jsonDel() {
        if (redisCache.isConnected()) {
            String json = jsonb.toJson(ClientRedisMapper.toRedis(client));
            String key = "client:" + client.getId().toString();
            redisCache.jsonSet(key, json);
            long res = redisCache.jsonDel(key);
            assertEquals(1, res);
            assertNull(redisCache.jsonGet(key));
        }
    }

    @Test
    void invalidateCache() {
        if (redisCache.isConnected()) {
            String json = jsonb.toJson(ClientRedisMapper.toRedis(client));
            String key = "client:" + client.getId().toString();
            redisCache.jsonSet(key, json);
            Object obj = redisCache.jsonGet("client:" + client.getId().toString());
            json = jsonb.toJson(obj);
            Client client1 = ClientRedisMapper.fromRedis(jsonb.fromJson(json, ClientRedis.class));
            assertEquals(client.getId(), client1.getId());
            redisCache.invalidateCache();
            assertNull(redisCache.jsonGet(key));
        }
    }
}