package org.example.model.domain.accounts;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import org.example.model.domain.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@CqlName("junior_accounts")
@Entity
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
public class JuniorAccount extends BankAccount {
    @CqlName("parent_id")
    UUID parentId;

    public JuniorAccount(UUID parentId,
                UUID id,
                UUID clientId,
                LocalDate creationDate,
                BigDecimal balance,
                Boolean isActive,
                LocalDate closeDate) {
            super(id, balance, clientId, isActive, creationDate, closeDate);
            this.parentId = parentId;
        }

    public JuniorAccount(UUID clientId, UUID parentId) {
        super(clientId);
        this.parentId = parentId;
//         TODO: Move logic to manager
//
//        LocalDate today = LocalDate.now();
//        long age = ChronoUnit.YEARS.between(client.getDateOfBirth(), today);
//        if (age < 18) {
//            this.parent = parent;
//        } else {
//            throw new IllegalArgumentException("Client must be under 18 years old to open a junior account");
//        }
    }

        @CqlName("account_id")
        @Override
        public UUID getId () {
            return super.getId();
        }

        public UUID getParentId () {
            return parentId;
        }
    }
