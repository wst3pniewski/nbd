package org.example.model.accounts;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


public class StandardAccount extends BankAccount {

    BigDecimal debitLimit;

    BigDecimal debit;

    public StandardAccount(Client client, BigDecimal debitLimit) {
        super(client);
        LocalDate today = LocalDate.now();
        long age = ChronoUnit.YEARS.between(client.getDateOfBirth(), today);
        if (age >= 18) {
            this.debitLimit = debitLimit;
            this.debit = new BigDecimal(0);
        } else {
            throw new IllegalArgumentException("Client must be at least 18 years old to open a standard account");
        }
    }

    public StandardAccount(UUID id, BigDecimal balance, Client client, Boolean isActive, LocalDate creationDate, LocalDate closeDate, BigDecimal debitLimit, BigDecimal debit) {
        super(id, balance, client, isActive, creationDate, closeDate);
        this.debitLimit = debitLimit;
        this.debit = debit;
    }


    public BigDecimal getDebitLimit() {
        return debitLimit;
    }

    public void setDebitLimit(BigDecimal debitLimit) {
        this.debitLimit = debitLimit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }
}
