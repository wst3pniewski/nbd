package org.example.model.accounts;


import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@BsonDiscriminator(key = "_clazz", value = "junior")
public class JuniorAccount extends BankAccount {

    @BsonProperty("parent")
    Client parent;

    @BsonCreator
    public JuniorAccount(@BsonProperty("_id") UUID id,
                         @BsonProperty("balance") BigDecimal balance,
                         @BsonProperty("client") Client client,
                         @BsonProperty("active") Boolean isActive,
                         @BsonProperty("creationDate") LocalDate creationDate,
                         @BsonProperty("closeDate") LocalDate closeDate,
                         @BsonProperty("parent") Client parent) {
        super(id, balance, client, isActive, creationDate, closeDate);
        LocalDate today = LocalDate.now();
        long age = ChronoUnit.YEARS.between(client.getDateOfBirth(), today);
        if (age < 18) {
            this.parent = parent;
        } else {
            throw new IllegalArgumentException("Client must be under 18 years old to open a junior account");
        }
    }

    public JuniorAccount(Client client, Client parent) {
        super(client);
        this.parent = parent;
    }

    public Client getParent() {
        return parent;
    }
}
