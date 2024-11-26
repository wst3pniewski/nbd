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

@BsonDiscriminator(key = "_clazz", value = "saving")
public class SavingAccountDTO extends BankAccountDTO {
    @JsonProperty("interestRate")
    @BsonProperty("interestRate")
    BigDecimal interestRate;

    @JsonCreator
    @BsonCreator
    public SavingAccountDTO(@JsonProperty("id") @BsonProperty("_id") UUID id,
                            @JsonProperty("balance") @BsonProperty("balance") BigDecimal balance,
                            @JsonProperty("client") @BsonProperty("client") ClientDTO client,
                            @JsonProperty("active") @BsonProperty("active") Boolean isActive,
                            @JsonProperty("creationDate") @BsonProperty("creationDate") LocalDate creationDate,
                            @JsonProperty("closeDate") @BsonProperty("closeDate") LocalDate closeDate,
                            @JsonProperty("interestRate") @BsonProperty("interestRate") BigDecimal interestRate) {
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
