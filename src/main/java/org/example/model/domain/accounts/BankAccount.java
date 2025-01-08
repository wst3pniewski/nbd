package org.example.model.domain.accounts;

import com.datastax.oss.driver.api.mapper.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity(defaultKeyspace = "bank_accounts")
@PropertyStrategy(mutable = false)
@CqlName("bank_account")
public class BankAccount {

    @PartitionKey
    @CqlName("account_id")
    protected UUID accountId;

    protected BigDecimal balance;

//    @ClusteringColumn
    @CqlName("client_id")
    protected UUID clientId;

    @CqlName("is_active")
    protected Boolean isActive;

    @CqlName("creation_date")
    protected LocalDate creationDate;

    @CqlName("close_date")
    protected LocalDate closeDate;

    protected String discriminator;

    public BankAccount(UUID accountId,
                       UUID clientId,
                       LocalDate creationDate,
                       BigDecimal balance,
                       String discriminator,
                       Boolean isActive,
                       LocalDate closeDate) {
        this.accountId = accountId;
        this.balance = balance;
        this.clientId = clientId;
        this.isActive = isActive;
        this.creationDate = creationDate;
        this.closeDate = closeDate;
        this.discriminator = discriminator;
    }

    public BankAccount(UUID clientId) {
        this.accountId = UUID.randomUUID();
        this.clientId = clientId;
        this.balance = new BigDecimal(0);
        this.isActive = true;
        this.creationDate = LocalDate.now();
        this.closeDate = null;
        this.discriminator = "BANKACCOUNT";
    }

    @PartitionKey
    @CqlName("account_id")
    public UUID getId() {
        return this.accountId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setBalance(BigDecimal balance) {
        if (balance.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    @CqlName("is_active")
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
