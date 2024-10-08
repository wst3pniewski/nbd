package org.example.model.accounts;

import jakarta.persistence.*;
import org.example.model.clients.Client;

@Entity
@Access(AccessType.FIELD)
public class JuniorAccount extends BankAccount{
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    Client parent;

    public JuniorAccount(Client client, Client parent) {
        super(client);
        this.parent = parent;
    }

    @Override
    public String getAccountType() {
        return "";
    }

    @Override
    public String getAccountInfo() {
        return "";
    }
}
