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

@CqlName("saving_accounts")
@Entity
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
public class SavingAccount extends BankAccount {

    @CqlName("interest_rate")
    BigDecimal interestRate;

    public SavingAccount(UUID id,
                         UUID clientId,
                         BigDecimal interestRate,
                         LocalDate creationDate,
                         BigDecimal balance,
                         Boolean isActive,
                         LocalDate closeDate) {
        super(id, balance, clientId, isActive, creationDate, closeDate);
        this.interestRate = interestRate;
    }

    public SavingAccount(UUID clientId, BigDecimal interestRate) {
        super(clientId);
        this.interestRate = interestRate;
//        LocalDate today = LocalDate.now();
//        long age = ChronoUnit.YEARS.between(client.getDateOfBirth(), today);
//        if (age >= 18) {
//            this.interestRate = interestRate;
//        } else {
//            throw new IllegalArgumentException("Client must be at least 18 years old to open a saving account");
//        }
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
