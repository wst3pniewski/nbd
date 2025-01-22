package org.example.model.mappers;

import org.example.model.Transaction;
import org.example.model.kafka.TransactionKafka;

public class TransactionKafkaMapper {
    public static TransactionKafka toKafka(Transaction transaction) {
        return new TransactionKafka(
                transaction.getId().toString(),
                transaction.getSourceAccount().toString(),
                transaction.getDestinationAccount().toString(),
                transaction.getAmount()
        );
    }

    public static Transaction fromKafka(TransactionKafka transactionKafka) {
        return new Transaction(
                java.util.UUID.fromString(transactionKafka.getId()),
                java.util.UUID.fromString(transactionKafka.getSourceAccount()),
                java.util.UUID.fromString(transactionKafka.getDestinationAccount()),
                transactionKafka.getAmount()
        );
    }
}
