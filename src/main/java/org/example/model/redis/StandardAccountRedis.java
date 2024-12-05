package org.example.model.redis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


public class StandardAccountRedis extends BankAccountRedis {
    BigDecimal debitLimit;

    BigDecimal debit;

    @JsonCreator
    public StandardAccountRedis(@JsonProperty("id") UUID id,
                                @JsonProperty("balance") BigDecimal balance,
                                @JsonProperty("client") ClientRedis client,
                                @JsonProperty("active") Boolean isActive,
                                @JsonProperty("creationDate") LocalDate creationDate,
                                @JsonProperty("closeDate") LocalDate closeDate,
                                @JsonProperty("debitLimit") BigDecimal debitLimit,
                                @JsonProperty("debit") BigDecimal debit) {
        super(id, balance, client, isActive, creationDate, closeDate);
        this.debitLimit = debitLimit;
        this.debit = debit;
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
