package org.example.model.accounts;


import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.clients.Client;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@BsonDiscriminator(key = "_clazz", value = "junior")
public class JuniorAccount extends BankAccount {

    @BsonProperty("parent")
    Client parent;

    @BsonCreator
    public JuniorAccount(@BsonProperty("_id") long id,
                         @BsonProperty("client") Client client,
                         @BsonProperty("parent") Client parent) {
        super(id, client);
        LocalDate today = LocalDate.now();
        long age = ChronoUnit.YEARS.between(client.getDateOfBirth(), today);
        if (age < 18) {
            this.parent = parent;
        } else {
            throw new IllegalArgumentException("Client must be under 18 years old to open a junior account");
        }
    }

    public Client getParent() {
        return parent;
    }
}
