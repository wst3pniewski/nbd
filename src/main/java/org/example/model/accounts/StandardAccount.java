package org.example.model.accounts;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import org.example.model.clients.Client;

import java.math.BigDecimal;

@Entity
@Access(AccessType.FIELD)
public class StandardAccount extends BankAccount{
    BigDecimal debitLimit;
    BigDecimal debit;

    public StandardAccount(Client client, BigDecimal debitLimit) {
        super(client);
        this.debitLimit = debitLimit;
        this.debit = new BigDecimal(0);
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

    @Override
    public String getAccountType() {
        return "standard";
    }

    @Override
    public String getAccountInfo() {
        return "";
    }
}
