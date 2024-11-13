package org.example.model;


import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.accounts.BankAccount;

import java.math.BigDecimal;
import java.util.UUID;


public class Transaction extends AbstractEntity {

    @BsonProperty("sourceAccount")
    BankAccount sourceAccount;

    @BsonProperty("destinationAccount")
    BankAccount destinationAccount;

    @BsonProperty("amount")
    BigDecimal amount;

    @BsonCreator
    public Transaction(@BsonProperty("_id") UUID id,
                       @BsonProperty("sourceAccount") BankAccount sourceAccount,
                       @BsonProperty("destinationAccount") BankAccount destinationAccount,
                       @BsonProperty("amount") BigDecimal amount) {
        super(id);
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public Transaction(BankAccount sourceAccount,
                       BankAccount destinationAccount,
                       BigDecimal amount) {
        super();
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public BankAccount getSourceAccount() {
        return sourceAccount;
    }

    public BankAccount getDestinationAccount() {
        return destinationAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public UUID getId() {
        return getEntityId();
    }
}
