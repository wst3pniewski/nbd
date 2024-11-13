package org.example.model.dto;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@BsonDiscriminator(key = "_clazz", value = "junior")
public class JuniorAccountDTO extends BankAccountDTO {
    @BsonProperty("parent")
    ClientDTO parent;

    @BsonCreator
    public JuniorAccountDTO(@BsonProperty("_id") UUID id,
                            @BsonProperty("balance") BigDecimal balance,
                            @BsonProperty("client") ClientDTO client,
                            @BsonProperty("active") Boolean isActive,
                            @BsonProperty("creationDate") LocalDate creationDate,
                            @BsonProperty("closeDate") LocalDate closeDate,
                            @BsonProperty("parent") ClientDTO parent) {
        super(id, balance, client, isActive, creationDate, closeDate);
        this.parent = parent;
    }

    public ClientDTO getParent() {
        return parent;
    }

    public void setParent(ClientDTO parent) {
        this.parent = parent;
    }
}
