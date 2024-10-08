package org.example.model;

import jakarta.persistence.*;
import org.example.model.accounts.BankAccount;

import java.math.BigDecimal;

@Entity
@Access(AccessType.FIELD)
public class Transaction {
    @OneToMany
    @JoinColumn(name = "source_account_id", nullable = false)
    BankAccount sourceAccount;

    @OneToMany
    @JoinColumn(name = "destination_account_id", nullable = false)
    BankAccount destinationAccount;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    BigDecimal amount;

    public Transaction(BankAccount sourceAccount, BankAccount destinationAccount, BigDecimal amount) {
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
}
