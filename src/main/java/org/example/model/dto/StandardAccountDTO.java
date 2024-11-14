package org.example.model.dto;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@BsonDiscriminator(key = "_clazz", value = "standard")
public class StandardAccountDTO extends BankAccountDTO {
    @BsonProperty("debitLimit")
    BigDecimal debitLimit;

    @BsonProperty("debit")
    BigDecimal debit;

    @BsonCreator
    public StandardAccountDTO(@BsonProperty("_id") UUID id,
                              @BsonProperty("balance") BigDecimal balance,
                              @BsonProperty("client") ClientDTO client,
                              @BsonProperty("active") Boolean isActive,
                              @BsonProperty("creationDate") LocalDate creationDate,
                              @BsonProperty("closeDate") LocalDate closeDate,
                              @BsonProperty("debitLimit") BigDecimal debitLimit,
                              @BsonProperty("debit") BigDecimal debit) {
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
