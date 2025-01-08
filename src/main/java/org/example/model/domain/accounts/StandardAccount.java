package org.example.model.domain.accounts;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity(defaultKeyspace = "bank_accounts")
@PropertyStrategy(mutable = false)
@CqlName("bank_account")
public class StandardAccount extends BankAccount {

    @CqlName("debit_limit")
    BigDecimal debitLimit;

    BigDecimal debit;

    public StandardAccount(UUID id,
                           BigDecimal debitLimit,
                           BigDecimal debit,
                           UUID clientId,
                           LocalDate creationDate,
                           BigDecimal balance,
                           String discriminator,
                           Boolean isActive,
                           LocalDate closeDate) {
        super(id, clientId, creationDate, balance, discriminator, isActive, closeDate);
        this.debitLimit = debitLimit;
        this.debit = debit;
    }

    public StandardAccount(UUID clientId, BigDecimal debitLimit) {
        super(clientId);
        this.debitLimit = debitLimit;
        this.debit = new BigDecimal(0);
        this.discriminator = "STANDARD";
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