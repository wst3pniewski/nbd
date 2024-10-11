package org.example.model.accounts;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import org.example.model.clients.Client;

import java.math.BigDecimal;

@Entity
@Access(AccessType.FIELD)
public class SavingAccount extends BankAccount {

    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 15, fraction = 2)
    BigDecimal interestRate;

    public SavingAccount(Client client, BigDecimal interestRate) {
        super(client);
        this.interestRate = interestRate;
    }

    @Override
    public String getAccountType() {
        return "saving";
    }

    @Override
    public String getAccountInfo() {
        return "";
    }
}
