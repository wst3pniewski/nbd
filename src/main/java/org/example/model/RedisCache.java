package org.example.model;

import redis.clients.jedis.JedisPooled;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class RedisCache implements AutoCloseable {
    private JedisPooled jedis;

    public RedisCache() {
        initDbConnection();
        this.invalidateCache();
    }

    private void initDbConnection() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("application.properties")) {
            properties.load(fis);

            String url = properties.getProperty("redis.url");
            this.jedis = new JedisPooled(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String jsonSet(String key, Object object) {
        return jedis.jsonSet(key, object);
    }

    public Object jsonGet(String key) {
        return jedis.jsonGet(key);
    }

    public long jsonDel(String key) {
        return jedis.jsonDel(key);
    }

    public JedisPooled getJedis() {
        return jedis;
    }

    public void invalidateCache() {
        try {
            if (jedis.ping().equals("PONG")) {
                jedis.flushDB();
            } else {
                System.err.println("Failed to connect to Redis.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while invalidating the cache: " + e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        jedis.close();
    }
}
