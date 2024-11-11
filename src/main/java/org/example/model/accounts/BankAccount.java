package org.example.model.accounts;


import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.AbstractEntity;
import org.example.model.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;


public abstract class BankAccount extends AbstractEntity {

    @BsonProperty("balance")
    BigDecimal balance;

    @BsonProperty("client")
    Client client;

    @BsonProperty("isActive")
    Boolean isActive;

    @BsonProperty("creationDate")
    LocalDate creationDate;

    @BsonProperty("closeDate")
    LocalDate closeDate = null;

    @BsonCreator
    public BankAccount(@BsonProperty("_id") long id,
                       @BsonProperty("client") Client client) {
        super(id);
        this.client = client;
        this.balance = new BigDecimal(0);
        this.isActive = true;
        this.creationDate = LocalDate.now();
    }

    public Client getClient() {
        return client;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        if (balance.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
        this.isActive = false;
    }

    public long getAccountId() {
        return getEntityId();
    }
}
