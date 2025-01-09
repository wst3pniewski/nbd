package org.example.model.domain;


import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;

import java.math.BigDecimal;
import java.util.UUID;


@CqlName("transactions")
@Entity
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
public class Transaction {

    private UUID transactionId;

    @CqlName("source_account")
    private UUID sourceAccount;

    @CqlName("destination_account")
    private UUID destinationAccount;

    private BigDecimal amount;


    public Transaction(UUID sourceAccount,
                       UUID destinationAccount,
                       BigDecimal amount) {
        this.transactionId = UUID.randomUUID();
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public Transaction(UUID transactionId,
                       UUID sourceAccount,
                       UUID destinationAccount,
                       BigDecimal amount) {
        this.transactionId = transactionId;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public UUID getSourceAccount() {
        return sourceAccount;
    }

    public UUID getDestinationAccount() {
        return destinationAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @PartitionKey
    @CqlName("transaction_id")
    public UUID getId() {
        return this.transactionId;
    }
}
