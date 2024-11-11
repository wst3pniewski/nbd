package org.example.model.accounts;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class StandardAccount extends BankAccount {

    @BsonProperty("debitLimit")
    BigDecimal debitLimit;

    @BsonProperty("debit")
    BigDecimal debit;

    @BsonCreator
    public StandardAccount(@BsonProperty("_id") long id,
                           @BsonProperty("client") Client client,
                           @BsonProperty("debitLimit") BigDecimal debitLimit) {
        super(id, client);
        LocalDate today = LocalDate.now();
        long age = ChronoUnit.YEARS.between(client.getDateOfBirth(), today);
        if (age >= 18) {
            this.debitLimit = debitLimit;
            this.debit = new BigDecimal(0);
        } else {
            throw new IllegalArgumentException("Client must be at least 18 years old to open a standard account");
        }
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
