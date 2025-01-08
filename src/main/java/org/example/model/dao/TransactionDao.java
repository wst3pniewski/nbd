package org.example.model.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.domain.Transaction;
import org.example.model.domain.clients.Client;

import java.util.UUID;

@Dao
public interface TransactionDao {
    @Insert
    void create(Transaction transaction);

    @Select
    Transaction findById(UUID transactionId);

    @Query("SELECT * FROM bank_accounts.transactions")
    PagingIterable<Transaction> findAll();

    @Delete
    void delete(Transaction transaction);
}
