package org.example.model.dto;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.AbstractEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionDTO extends AbstractEntity {
    @BsonProperty("sourceAccount")
    UUID sourceAccount;

    @BsonProperty("destinationAccount")
    UUID destinationAccount;

    @BsonProperty("amount")
    BigDecimal amount;

    @JsonbCreator
    @BsonCreator
    public TransactionDTO(@JsonbProperty("id") @BsonProperty("_id") UUID id,
                          @BsonProperty("sourceAccount") UUID sourceAccount,
                          @BsonProperty("destinationAccount") UUID destinationAccount,
                          @BsonProperty("amount") BigDecimal amount) {
        super(id);
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public UUID getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(UUID sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public UUID getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(UUID destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getId() {
        return getEntityId();
    }
}
