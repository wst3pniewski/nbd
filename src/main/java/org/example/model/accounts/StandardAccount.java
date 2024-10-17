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

@Entity
@Access(AccessType.FIELD)
public class StandardAccount extends BankAccount {

    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 15, fraction = 2)
    BigDecimal debitLimit;

    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 15, fraction = 2)
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

    public StandardAccount() {
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
