package org.example.model.mappers;

import org.example.model.Transaction;
import org.example.model.redis.TransactionRedis;

public class TransactionRedisMapper {


    public static TransactionRedis toRedis(Transaction transaction) {
        return new TransactionRedis(
                transaction.getId(),
                transaction.getSourceAccount(),
                transaction.getDestinationAccount(),
                transaction.getAmount()
        );
    }

    public static Transaction fromRedis(TransactionRedis transactionRedis) {
        return new Transaction(
                transactionRedis.getId(),
                transactionRedis.getSourceAccount(),
                transactionRedis.getDestinationAccount(),
                transactionRedis.getAmount()
        );
    }
}
