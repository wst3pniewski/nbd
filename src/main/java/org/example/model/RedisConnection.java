package org.example.model;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.UnifiedJedis;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class RedisConnection implements AutoCloseable {
    private JedisPooled jedis;

    public RedisConnection() {
        initDbConnection();
    }

    private void initDbConnection() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("redis.properties")) {
            properties.load(fis);

            String url = properties.getProperty("redis.url");
            this.jedis = new JedisPooled(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testConnection() {
            String res1 = jedis.set("bike:1", "Deimos");
            System.out.println(res1); // OK

            String res2 = jedis.get("bike:1");
            System.out.println(res2); // Deimos
    }

    @Override
    public void close() throws Exception {
        jedis.close();
    }
}
