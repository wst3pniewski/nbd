package org.example.model;

import java.math.BigDecimal;
import java.util.UUID;


public class Transaction extends AbstractEntity {

    UUID sourceAccount;

    UUID destinationAccount;

    BigDecimal amount;


    public Transaction(UUID sourceAccount,
                       UUID destinationAccount,
                       BigDecimal amount) {
        super();
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public Transaction(UUID id,
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

    public UUID getDestinationAccount() {
        return destinationAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public UUID getId() {
        return getEntityId();
    }
}
