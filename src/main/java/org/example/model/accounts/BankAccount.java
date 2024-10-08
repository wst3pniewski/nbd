package org.example.model.accounts;

import jakarta.persistence.*;
import org.example.model.AbstractEntity;
import org.example.model.clients.Client;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Access(AccessType.FIELD)
public abstract class BankAccount extends AbstractEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(
            name = "accountIdSequence",
            initialValue = 1
    )
    @GeneratedValue(generator = "accountIdSequence")
    private long id;
    BigDecimal balance;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    Client client;

    Boolean isActive;

    Date creationDate;
    Date closeDate;

    public BankAccount() {
    }

    public BankAccount(Client client){
        this.client = client;
        this.balance = new BigDecimal(0);
        this.isActive = true;
        this.creationDate = new Date();
    }
    public abstract String getAccountType();
    public abstract String getAccountInfo();

    public Client getClient() {
        return client;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public long getAccountId() {
        return id;
    }
}
