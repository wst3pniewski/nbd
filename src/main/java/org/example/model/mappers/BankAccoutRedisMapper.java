package org.example.model.mappers;

import org.example.model.accounts.BankAccount;
import org.example.model.accounts.JuniorAccount;
import org.example.model.accounts.SavingAccount;
import org.example.model.accounts.StandardAccount;
import org.example.model.redis.BankAccountRedis;
import org.example.model.redis.JuniorAccountRedis;
import org.example.model.redis.SavingAccountRedis;
import org.example.model.redis.StandardAccountRedis;

public class BankAccoutRedisMapper {
    public static BankAccountRedis toRedis(BankAccount bankAccount) {
        return switch (bankAccount.getClass().getSimpleName()) {
            case "SavingAccount" -> SavingAccountRedisMapper.toRedis((SavingAccount) bankAccount);
            case "JuniorAccount" -> JuniorAccountRedisMapper.toRedis((JuniorAccount) bankAccount);
            case "StandardAccount" -> StandardAccountRedisMapper.toRedis((StandardAccount) bankAccount);
            default ->
                    throw new IllegalArgumentException("Unsupported BankAccount type: " + bankAccount.getClass().getName());
        };
    }

    public static BankAccount fromRedis(BankAccountRedis bankAccountRedis) {
        return switch (bankAccountRedis.getClass().getSimpleName()) {
            case "SavingAccountRedis" -> SavingAccountRedisMapper.fromRedis((SavingAccountRedis) bankAccountRedis);
            case "JuniorAccountRedis" -> JuniorAccountRedisMapper.fromRedis((JuniorAccountRedis) bankAccountRedis);
            case "StandardAccountRedis" -> StandardAccountRedisMapper.fromRedis((StandardAccountRedis) bankAccountRedis);
            default ->
                    throw new IllegalArgumentException("Unsupported BankAccount type: " + bankAccountRedis.getClass().getName());
        };
    }
}
