package org.example.model.clients;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Access(AccessType.FIELD)
public abstract class ClientType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int maxAccounts;
    private String accountType;

    public int getMaxActiveAccounts() {
        return maxAccounts;
    }
    public String getTypeInfo() {
        return accountType;
    }

    public ClientType() {
    }

    public ClientType(int maxAccounts, String accountType) {
        this.maxAccounts = maxAccounts;
        this.accountType = accountType;
    }
}
