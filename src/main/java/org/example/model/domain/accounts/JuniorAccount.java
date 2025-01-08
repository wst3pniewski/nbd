package org.example.model.domain.accounts;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity(defaultKeyspace = "bank_accounts")
@PropertyStrategy(mutable = false)
@CqlName("bank_account")
public class JuniorAccount extends BankAccount {
    @CqlName("parent_id")
    UUID parentId;

    public JuniorAccount(UUID id,
                         UUID clientId,
                         UUID parentId,
                         LocalDate creationDate,
                         BigDecimal balance,
                         String discriminator,
                         Boolean isActive,
                         LocalDate closeDate) {
        super(id, clientId, creationDate, balance, discriminator, isActive, closeDate);
        this.parentId = parentId;
    }

    public JuniorAccount(UUID clientId, UUID parentId) {
        super(clientId);
        this.parentId = parentId;
        this.discriminator = "JUNIOR";
    }

    @CqlName("account_id")
    @Override
    public UUID getId() {
        return super.getId();
    }

    public UUID getParentId() {
        return parentId;
    }
}