package org.example.model.redis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mongodb.lang.Nullable;
import org.example.model.AbstractEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class BankAccountRedis extends AbstractEntity {

    BigDecimal balance;

    ClientRedis client;

    @JsonProperty("active")
    Boolean isActive;

    LocalDate creationDate;

    LocalDate closeDate;

    @JsonCreator
    public BankAccountRedis(@JsonProperty("id") UUID id,
                            BigDecimal balance,
                            ClientRedis client,
                            @JsonProperty("active") Boolean isActive,
                            LocalDate creationDate,
                            LocalDate closeDate
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

    public ClientRedis getClient() {
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

    public void setClient(ClientRedis client) {
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