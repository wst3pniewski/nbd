package org.example.model.redis;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.example.model.AbstractEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionRedis extends AbstractEntity {
    UUID sourceAccount;

    UUID destinationAccount;

    BigDecimal amount;

    @JsonbCreator
    public TransactionRedis(@JsonbProperty("id") UUID id,
                            UUID sourceAccount,
                            UUID destinationAccount,
                            BigDecimal amount) {
        super(id);
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public UUID getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(UUID sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public UUID getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(UUID destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getId() {
        return getEntityId();
    }
}

