package org.example.model.repositories;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.example.model.RedisCache;
import org.example.model.Transaction;
import org.example.model.dto.TransactionDTO;
import org.example.model.mappers.TransactionDTOMapper;

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
        try {
            String json = jsonb.toJson(TransactionDTOMapper.toDTO(transaction));
            String res = redisCache.jsonSet(hashPrefix + transaction.getId().toString(), json);
            if (Objects.equals(res, "OK")) {
                return TransactionDTOMapper.fromDTO(jsonb.fromJson(json, TransactionDTO.class));
            }
        } catch (Exception e) {
            System.err.println("Failed write to Redis: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Transaction> findAll() {
        return mongoTransactionRepository.findAll();
    }

    @Override
    public Transaction findById(UUID id) {
        try{
            Object obj = redisCache.jsonGet(hashPrefix + id.toString());
            if (obj != null) {
                String json = jsonb.toJson(obj);
                return TransactionDTOMapper.fromDTO(jsonb.fromJson(json, TransactionDTO.class));
            }
            // TODO: catch specific exception
        } catch (Exception e) {
            System.err.println("Redis connection failed, falling back to MongoDB: " + e.getMessage());
        }

        Transaction transaction = mongoTransactionRepository.findById(id);
        if (transaction != null) {
            try {
                String json = jsonb.toJson(transaction);
                redisCache.jsonSet(hashPrefix + transaction.getId().toString(), json);
                // TODO: catch specific exception
            } catch (Exception e) {
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
        try{
            String json = jsonb.toJson(transaction);
            redisCache.jsonSet(hashPrefix + transaction.getId().toString(), json);
            return transaction;
        } catch (Exception e) {
            System.err.println("Failed write to Redis: " + e.getMessage());
        }
        mongoTransactionRepository.update(transaction);
        return transaction;
    }

    public boolean delete(UUID id){
        Boolean mongoUpdStatus = mongoTransactionRepository.delete(id);
        try{
            long res = redisCache.jsonDel(hashPrefix + id.toString());
            return res != 0;
        } catch (Exception e) {
            System.err.println("Failed write to Redis: " + e.getMessage());
        }
        return mongoUpdStatus;
    }
}
