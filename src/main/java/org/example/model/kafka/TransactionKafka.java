package org.example.model.kafka;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionKafka {
    String id;

    String sourceAccount;

    String destinationAccount;

    BigDecimal amount;

    public TransactionKafka(String sourceAccount, String destinationAccount, BigDecimal amount) {
        this.id = UUID.randomUUID().toString();
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public TransactionKafka(String id, String sourceAccount, String destinationAccount, BigDecimal amount) {
        this.id = id;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
