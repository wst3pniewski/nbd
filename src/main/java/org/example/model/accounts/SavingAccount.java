package org.example.model.accounts;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import org.example.model.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class SavingAccount extends BankAccount {

    BigDecimal interestRate;

    public SavingAccount(Client client, BigDecimal interestRate) {
        super(client);
        LocalDate today = LocalDate.now();
        long age = ChronoUnit.YEARS.between(client.getDateOfBirth(), today);
        if (age >= 18){
            this.interestRate = interestRate;
        } else {
            throw new IllegalArgumentException("Client must be at least 18 years old to open a saving account");
        }
    }

    public SavingAccount() {
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
