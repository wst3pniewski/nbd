package org.example.model.accounts;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class SavingAccount extends BankAccount {

    @BsonProperty("interestRate")
    BigDecimal interestRate;

    @BsonCreator
    public SavingAccount(@BsonProperty("_id") long id,
                         @BsonProperty("client") Client client,
                         @BsonProperty("interestRate") BigDecimal interestRate) {
        super(id, client);
        LocalDate today = LocalDate.now();
        long age = ChronoUnit.YEARS.between(client.getDateOfBirth(), today);
        if (age >= 18) {
            this.interestRate = interestRate;
        } else {
            throw new IllegalArgumentException("Client must be at least 18 years old to open a saving account");
        }
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
