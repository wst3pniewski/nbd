package org.example.model.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.RedisCache;
import org.example.model.accounts.BankAccount;
import org.example.model.mappers.BankAccoutRedisMapper;
import org.example.model.redis.BankAccountRedis;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CachedAccountRepository implements Repository<BankAccount> {
    private final RedisCache redisCache;
    private final AccountRepository mongoAccountRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String hashPrefix = "account:";

    public CachedAccountRepository(RedisCache redisCache, AccountRepository mongoAccountRepository) {
        this.redisCache = redisCache;
        this.mongoAccountRepository = mongoAccountRepository;
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    }

    @Override
    public BankAccount add(BankAccount bankAccount) {
        if (bankAccount == null) {
            return null;
        }

        mongoAccountRepository.add(bankAccount);
        if (redisCache.isConnected()) {
            try {
                String json = objectMapper.writeValueAsString(BankAccoutRedisMapper.toRedis(bankAccount));
                String res = redisCache.jsonSet(hashPrefix + bankAccount.getId().toString(), json);
                if (Objects.equals(res, "OK")) {
                    return BankAccoutRedisMapper.fromRedis(objectMapper.readValue(json, BankAccountRedis.class));
                }
            } catch (Exception e) {
                System.err.println("Failed write to Redis: " + e.getMessage());
            }
        }

        return null;
    }

    @Override
    public List<BankAccount> findAll() {
        return mongoAccountRepository.findAll();
    }

    @Override
    public BankAccount findById(UUID id) {
        if (redisCache.isConnected()) {
            try{
                Object obj = redisCache.jsonGet(hashPrefix + id.toString());
                if (obj != null) {
                    String json = objectMapper.writeValueAsString(obj);
                    return BankAccoutRedisMapper.fromRedis(objectMapper.readValue(json, BankAccountRedis.class));
                }
                // TODO: catch specific exception
            } catch (Exception e) {
                System.err.println("Redis connection failed, falling back to MongoDB: " + e.getMessage());
            }
        }

        BankAccount bankAccount = mongoAccountRepository.findById(id);
        if (bankAccount != null & redisCache.isConnected()) {
            try {
                String json = objectMapper.writeValueAsString(BankAccoutRedisMapper.toRedis(bankAccount));
                redisCache.jsonSet(hashPrefix + bankAccount.getId().toString(), json);
                // TODO: catch specific exception
            } catch (Exception e) {
                System.err.println("Failed write to Redis: " + e.getMessage());
            }
        }


        return bankAccount;
    }

    @Override
    public BankAccount update(BankAccount bankAccount) {
        if (bankAccount == null) {
            return null;
        }
        if (redisCache.isConnected()) {
            try{
                String json = objectMapper.writeValueAsString(BankAccoutRedisMapper.toRedis(bankAccount));
                redisCache.jsonSet(hashPrefix + bankAccount.getId().toString(), json);
                return bankAccount;
            } catch (Exception e) {
                System.err.println("Failed write to Redis: " + e.getMessage());
            }
        }
        mongoAccountRepository.update(bankAccount);
        return bankAccount;
    }
}
