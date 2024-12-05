package org.example.model.redis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.model.dto.BankAccountDTO;
import org.example.model.dto.ClientDTO;
import org.example.model.mappers.ClientRedisMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class JuniorAccountRedis extends BankAccountRedis {
    @JsonProperty("parent")
    ClientRedis parent;

    @JsonCreator
    public JuniorAccountRedis(@JsonProperty("id") UUID id,
                              @JsonProperty("balance") BigDecimal balance,
                              @JsonProperty("client") ClientRedis client,
                              @JsonProperty("active") Boolean isActive,
                              @JsonProperty("creationDate") LocalDate creationDate,
                              @JsonProperty("closeDate") LocalDate closeDate,
                              @JsonProperty("parent") ClientRedis parent) {
        super(id, balance, client, isActive, creationDate, closeDate);
        this.parent = parent;
    }

    public ClientRedis getParent() {
        return parent;
    }

    public void setParent(ClientRedis parent) {
        this.parent = parent;
    }
}

