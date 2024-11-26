package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
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

    @JsonCreator
    @BsonCreator
    public StandardAccountDTO(@JsonProperty("id") @BsonProperty("_id") UUID id,
                              @JsonProperty("balance") @BsonProperty("balance") BigDecimal balance,
                              @JsonProperty("client") @BsonProperty("client") ClientDTO client,
                              @JsonProperty("active") @BsonProperty("active") Boolean isActive,
                              @JsonProperty("creationDate") @BsonProperty("creationDate") LocalDate creationDate,
                              @JsonProperty("closeDate") @BsonProperty("closeDate") LocalDate closeDate,
                              @JsonProperty("debitLimit") @BsonProperty("debitLimit") BigDecimal debitLimit,
                              @JsonProperty("debit") @BsonProperty("debit") BigDecimal debit) {
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
