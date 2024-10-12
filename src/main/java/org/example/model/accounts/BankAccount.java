package org.example.model.accounts;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import org.example.model.AbstractEntity;
import org.example.model.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Access(AccessType.FIELD)
public abstract class BankAccount extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "accountIdSequence", initialValue = 1)
    @GeneratedValue(generator = "accountIdSequence")
    private long id;

    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 15, fraction = 2)
    BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    Client client;

    Boolean isActive;
    LocalDate creationDate;
    LocalDate closeDate = null;

    public BankAccount() {
    }

    public BankAccount(Client client) {
        this.client = client;
        this.balance = new BigDecimal(0);
        this.isActive = true;
        this.creationDate = LocalDate.now();
    }

    public abstract String getAccountType();

    public abstract String getAccountInfo();

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
    }

    public long getAccountId() {
        return id;
    }
}
