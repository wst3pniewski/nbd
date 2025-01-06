package org.example.model.domain.accounts;

import org.example.model.domain.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class SavingAccount extends BankAccount {

    BigDecimal interestRate;

    public SavingAccount(Client client, BigDecimal interestRate) {
        super(client);
        LocalDate today = LocalDate.now();
        long age = ChronoUnit.YEARS.between(client.getDateOfBirth(), today);
        if (age >= 18) {
            this.interestRate = interestRate;
        } else {
            throw new IllegalArgumentException("Client must be at least 18 years old to open a saving account");
        }
    }

    public SavingAccount(UUID id,
                         BigDecimal balance,
                         Client client,
                         Boolean isActive,
                         LocalDate creationDate,
                         LocalDate closeDate,
                         BigDecimal interestRate) {
        super(id, balance, client, isActive, creationDate, closeDate);
        this.interestRate = interestRate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
