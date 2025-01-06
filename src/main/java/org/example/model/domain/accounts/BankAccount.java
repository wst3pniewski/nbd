package org.example.model.domain.accounts;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import org.example.model.domain.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public abstract class BankAccount {

    @PartitionKey
    @CqlName("account_id")
    UUID accountId;

    BigDecimal balance;

    Client client;

    @CqlName("is_active")
    Boolean isActive;

    @CqlName("creation_date")
    LocalDate creationDate;

    @CqlName("close_date")
    LocalDate closeDate;


    public BankAccount(Client client) {
        super();
        this.client = client;
        this.balance = new BigDecimal(0);
        this.isActive = true;
        this.creationDate = LocalDate.now();
//        this.closeDate = LocalDate.now();
        this.closeDate = null;
    }

    public BankAccount(UUID id, Client client) {
        this.accountId = id;
        this.client = client;
        this.balance = new BigDecimal(0);
        this.isActive = true;
        this.creationDate = LocalDate.now();
        this.closeDate = null;
    }

    public BankAccount(UUID id, BigDecimal balance, Client client, Boolean isActive, LocalDate creationDate, LocalDate closeDate) {
        this.accountId = id;
        this.balance = balance;
        this.client = client;
        this.isActive = isActive;
        this.creationDate = creationDate;
        this.closeDate = closeDate;
    }

    public UUID getId() {
        return this.accountId;
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
        if (!active) {
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
}
