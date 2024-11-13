package org.example.model.accounts;


import com.mongodb.lang.Nullable;
import org.bson.BsonNull;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.AbstractEntity;
import org.example.model.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@BsonDiscriminator(key = "_clazz", value = "BankAccount")
public abstract class BankAccount extends AbstractEntity {

    @BsonProperty("balance")
    BigDecimal balance;

    @BsonProperty("client")
    Client client;

    @BsonProperty("active")
    Boolean isActive;

    @BsonProperty("creationDate")
    LocalDate creationDate;

    @Nullable
    @BsonProperty("closeDate")
    LocalDate closeDate;

    @BsonCreator
    public BankAccount(@BsonProperty("_id") UUID id,
                       @BsonProperty("balance") BigDecimal balance,
                       @BsonProperty("client") Client client,
                       @BsonProperty("active") Boolean isActive,
                       @BsonProperty("creationDate") LocalDate creationDate,
                       @BsonProperty("closeDate") LocalDate closeDate) {
        super(id);
        this.balance = balance;
        this.client = client;
        this.isActive = isActive;
        this.creationDate = creationDate;
        this.closeDate = closeDate;
    }

    public BankAccount(Client client) {
//        super(id);
        super();
        this.client = client;
        this.balance = new BigDecimal(0);
        this.isActive = true;
        this.creationDate = LocalDate.now();
        this.closeDate = null;
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
        if (this.isActive == true) {
            this.isActive = false;
            this.closeDate = LocalDate.now();
        } else {
            this.isActive = true;
            this.closeDate = null;
        }
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
        this.isActive = false;
    }

    //    public long getAccountId() {
//        return getEntityId();
//    }
    public UUID getId() {
        return getEntityId();
    }
}
