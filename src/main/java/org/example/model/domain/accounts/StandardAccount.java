package org.example.model.domain.accounts;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import org.example.model.domain.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@CqlName("standard_accounts")
@Entity
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
public class StandardAccount extends BankAccount {

    @CqlName("debit_limit")
    BigDecimal debitLimit;

    BigDecimal debit;

    public StandardAccount(UUID id,
                           UUID clientId,
                           BigDecimal debitLimit,
                           BigDecimal debit,
                           LocalDate creationDate,
                           BigDecimal balance,
                           Boolean isActive,
                           LocalDate closeDate) {
        super(id, balance, clientId, isActive, creationDate, closeDate);
        this.debitLimit = debitLimit;
        this.debit = debit;
    }

    public StandardAccount(UUID clientId, BigDecimal debitLimit) {
        super(clientId);
        this.debitLimit = debitLimit;
        this.debit = new BigDecimal(0);
//        LocalDate today = LocalDate.now();
//        long age = ChronoUnit.YEARS.between(client.getDateOfBirth(), today);
//        if (age >= 18) {
//            this.debitLimit = debitLimit;
//            this.debit = new BigDecimal(0);
//        } else {
//            throw new IllegalArgumentException("Client must be at least 18 years old to open a standard account");
//        }
    }


    @CqlName("account_id")
    @Override
    public UUID getId() {
        return super.getId();
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
