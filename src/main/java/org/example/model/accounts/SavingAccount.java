package org.example.model.accounts;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import org.example.model.clients.Client;

import java.math.BigDecimal;

@Entity
@Access(AccessType.FIELD)
public class SavingAccount extends BankAccount {
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
