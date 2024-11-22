package org.example.model.dto;

import com.mongodb.lang.Nullable;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.AbstractEntity;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@BsonDiscriminator(key = "_clazz", value = "bankAccount")
public abstract class BankAccountDTO extends AbstractEntity {

    @BsonProperty("balance")
    BigDecimal balance;

    @BsonProperty("client")
    ClientDTO client;

    @BsonProperty("active")
    Boolean isActive;

    @BsonProperty("creationDate")
    LocalDate creationDate;

    @BsonProperty("closeDate")
    LocalDate closeDate;

    @BsonCreator
    public BankAccountDTO(@BsonProperty("_id") UUID id,
                          @BsonProperty("balance") BigDecimal balance,
                          @BsonProperty("client") ClientDTO client,
                          @BsonProperty("active") Boolean isActive,
                          @BsonProperty("creationDate") LocalDate creationDate,
                          @BsonProperty("closeDate") LocalDate closeDate
    ) {
        super(id);
        this.balance = balance;
        this.client = client;
        this.isActive = isActive;
        this.creationDate = creationDate;
        this.closeDate = closeDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public ClientDTO getClient() {
        return client;
    }

    public Boolean getActive() {
        return isActive;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public UUID getId() {
        return super.getEntityId();
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setCloseDate(@Nullable LocalDate closeDate) {
        this.closeDate = closeDate;
    }
}
