package org.example.model.repositories;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.example.model.RedisCache;
import org.example.model.Transaction;
import org.example.model.mappers.TransactionRedisMapper;
import org.example.model.redis.TransactionRedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CachedTransactionRepository implements Repository<Transaction> {
    private final RedisCache redisCache;
    private final TransactionRepository mongoTransactionRepository;
    private final Jsonb jsonb = JsonbBuilder.create();
    private final String hashPrefix = "transaction:";

    public CachedTransactionRepository(RedisCache redisCache, TransactionRepository mongoTransactionRepository) {
        this.redisCache = redisCache;
        this.mongoTransactionRepository = mongoTransactionRepository;
    }

    @Override
    public Transaction add(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        mongoTransactionRepository.add(transaction);
        if (redisCache.isConnected()) {
            try {
                String json = jsonb.toJson(TransactionRedisMapper.toRedis(transaction));
                String res = redisCache.jsonSet(hashPrefix + transaction.getId().toString(), json);
                int day24h = 86400;
                redisCache.expire(hashPrefix + transaction.getId().toString(), day24h);
                if (Objects.equals(res, "OK")) {
                    return TransactionRedisMapper.fromRedis(jsonb.fromJson(json, TransactionRedis.class));
                }
            } catch (JedisException e) {
                System.err.println("Failed write to Redis: " + e.getMessage());
            }
        }

        return null;
    }

    @Override
    public List<Transaction> findAll() {
        return mongoTransactionRepository.findAll();
    }

    @Override
    public Transaction findById(UUID id) {
        if (redisCache.isConnected()) {
            try{
                Object obj = redisCache.jsonGet(hashPrefix + id.toString());
                if (obj != null) {
                    String json = jsonb.toJson(obj);
                    return TransactionRedisMapper.fromRedis(jsonb.fromJson(json, TransactionRedis.class));
                }
            } catch (Exception e) {
                System.err.println("Redis connection failed, falling back to MongoDB: " + e.getMessage());
            }
        }

        Transaction transaction = mongoTransactionRepository.findById(id);
        if (transaction != null & redisCache.isConnected()) {
            try {
                String json = jsonb.toJson(TransactionRedisMapper.toRedis(transaction));
                redisCache.jsonSet(hashPrefix + transaction.getId().toString(), json);
                int day24h = 86400;
                redisCache.expire(hashPrefix + transaction.getId().toString(), day24h);
            } catch (JedisException e) {
                System.err.println("Failed write to Redis: " + e.getMessage());
            }
        }


        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        if (redisCache.isConnected()) {
            try{
                String json = jsonb.toJson(TransactionRedisMapper.toRedis(transaction));
                redisCache.jsonSet(hashPrefix + transaction.getId().toString(), json);
                int day24h = 86400;
                redisCache.expire(hashPrefix + transaction.getId().toString(), day24h);
                return transaction;
            } catch (JedisException e) {
                System.err.println("Failed write to Redis: " + e.getMessage());
            }
        }
        mongoTransactionRepository.update(transaction);
        return transaction;
    }

    public boolean delete(UUID id){
        Boolean mongoUpdStatus = mongoTransactionRepository.delete(id);
        if (redisCache.isConnected()) {
            try{
                long res = redisCache.jsonDel(hashPrefix + id.toString());
                return res != 0;
            } catch (JedisException e) {
                System.err.println("Failed write to Redis: " + e.getMessage());
            }
        }
        return mongoUpdStatus;
    }
}
