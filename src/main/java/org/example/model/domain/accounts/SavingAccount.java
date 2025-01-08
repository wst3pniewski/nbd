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
public class SavingAccount extends BankAccount {

    @CqlName("interest_rate")
    BigDecimal interestRate;

    public SavingAccount(UUID id,
                         BigDecimal interestRate,
                         UUID clientId,
                         LocalDate creationDate,
                         BigDecimal balance,
                         String discriminator,
                         Boolean isActive,
                         LocalDate closeDate) {
        super(id, clientId, creationDate, balance, discriminator, isActive, closeDate);
        this.interestRate = interestRate;
    }

    public SavingAccount(UUID clientId, BigDecimal interestRate) {
        super(clientId);
        this.interestRate = interestRate;
        this.discriminator = "SAVING";
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    @CqlName("account_id")
    @Override
    public UUID getId() {
        return super.getId();
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
