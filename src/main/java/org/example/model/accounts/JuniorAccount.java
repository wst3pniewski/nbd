package org.example.model.accounts;


import org.example.model.clients.Client;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class JuniorAccount extends BankAccount {

    Client parent;

    public JuniorAccount(Client client, Client parent) {
        super(client);
        LocalDate today = LocalDate.now();
        long age = ChronoUnit.YEARS.between(client.getDateOfBirth(), today);
        if (age < 18) {
            this.parent = parent;
        } else {
            throw new IllegalArgumentException("Client must be under 18 years old to open a junior account");
        }
    }

    public JuniorAccount() {
    }

    public Client getParent() {
        return parent;
    }
}
