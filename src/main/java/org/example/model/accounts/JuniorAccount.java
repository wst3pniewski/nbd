package org.example.model.accounts;

import org.example.model.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


public class JuniorAccount extends BankAccount {

    Client parent;

    public JuniorAccount(Client client, Client parent) {
        super(client);
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        if (parent == null) {
            throw new IllegalArgumentException("Parent cannot be null");
        }
        this.parent = parent;
        LocalDate today = LocalDate.now();
        long age = ChronoUnit.YEARS.between(client.getDateOfBirth(), today);
        if (age < 18) {
            this.parent = parent;
        } else {
            throw new IllegalArgumentException("Client must be under 18 years old to open a junior account");
        }
    }


    public JuniorAccount(UUID id,
                         BigDecimal balance,
                         Client client,
                         Boolean isActive,
                         LocalDate creationDate,
                         LocalDate closeDate,
                         Client parent) {
        super(id, balance, client, isActive, creationDate, closeDate);
        this.parent = parent;
    }

    public Client getParent() {
        return parent;
    }
}
