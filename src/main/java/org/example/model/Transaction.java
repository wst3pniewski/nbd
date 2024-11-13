package org.example.model;

import org.example.model.accounts.BankAccount;

import java.math.BigDecimal;
import java.util.UUID;


public class Transaction extends AbstractEntity {

    BankAccount sourceAccount;

    BankAccount destinationAccount;

    BigDecimal amount;


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
