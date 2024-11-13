package org.example.model.dto;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.AbstractEntity;
import org.example.model.accounts.BankAccount;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionDTO extends AbstractEntity {
    @BsonProperty("sourceAccount")
    BankAccount sourceAccount;

    @BsonProperty("destinationAccount")
    BankAccount destinationAccount;

    @BsonProperty("amount")
    BigDecimal amount;

    @BsonCreator
    public TransactionDTO(@BsonProperty("_id") UUID id,
                       @BsonProperty("sourceAccount") BankAccount sourceAccount,
                       @BsonProperty("destinationAccount") BankAccount destinationAccount,
                       @BsonProperty("amount") BigDecimal amount) {
        super(id);
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }
}
