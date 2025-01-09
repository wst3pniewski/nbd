package org.example.model.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.domain.Transaction;

import java.util.UUID;

@Dao
public interface TransactionDao {

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Insert
    void create(Transaction transaction);

    @StatementAttributes(consistencyLevel = "ONE", pageSize = 100)
    @Select
    Transaction findById(UUID transactionId);

    @StatementAttributes(consistencyLevel = "ONE", pageSize = 100)
    @Query("SELECT * FROM bank_accounts.transactions")
    PagingIterable<Transaction> findAll();

    @Delete
    void delete(Transaction transaction);
}
