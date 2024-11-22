package org.example.model.dto;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@BsonDiscriminator(key = "_clazz", value = "saving")
public class SavingAccountDTO extends BankAccountDTO {
    @BsonProperty("interestRate")
    BigDecimal interestRate;

    @BsonCreator
    public SavingAccountDTO(@BsonProperty("_id") UUID id,
                            @BsonProperty("balance") BigDecimal balance,
                            @BsonProperty("client") ClientDTO client,
                            @BsonProperty("active") Boolean isActive,
                            @BsonProperty("creationDate") LocalDate creationDate,
                            @BsonProperty("closeDate") LocalDate closeDate,
                            @BsonProperty("interestRate") BigDecimal interestRate) {
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
