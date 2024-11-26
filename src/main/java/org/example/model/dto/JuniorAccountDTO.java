package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@BsonDiscriminator(key = "_clazz", value = "junior")
public class JuniorAccountDTO extends BankAccountDTO {
    @JsonProperty("parent")
    @BsonProperty("parent")
    ClientDTO parent;

    @JsonCreator
    @BsonCreator
    public JuniorAccountDTO(@JsonProperty("id") @BsonProperty("_id") UUID id,
                            @JsonProperty("balance") @BsonProperty("balance") BigDecimal balance,
                            @JsonProperty("client") @BsonProperty("client") ClientDTO client,
                            @JsonProperty("active") @BsonProperty("active") Boolean isActive,
                            @JsonProperty("creationDate") @BsonProperty("creationDate") LocalDate creationDate,
                            @JsonProperty("closeDate") @BsonProperty("closeDate") LocalDate closeDate,
                            @JsonProperty("parent") @BsonProperty("parent") ClientDTO parent) {
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
