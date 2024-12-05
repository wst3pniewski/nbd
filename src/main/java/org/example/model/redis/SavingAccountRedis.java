package org.example.model.redis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


public class SavingAccountRedis extends BankAccountRedis {
    @JsonProperty("interestRate")
    BigDecimal interestRate;

    @JsonCreator
    public SavingAccountRedis(@JsonProperty("id") UUID id,
                              @JsonProperty("balance") BigDecimal balance,
                              @JsonProperty("client") ClientRedis client,
                              @JsonProperty("active") Boolean isActive,
                              @JsonProperty("creationDate") LocalDate creationDate,
                              @JsonProperty("closeDate") LocalDate closeDate,
                              @JsonProperty("interestRate") BigDecimal interestRate) {
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
