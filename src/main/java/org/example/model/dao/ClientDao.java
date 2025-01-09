package org.example.model.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.domain.clients.Client;

import java.util.UUID;

@Dao
public interface ClientDao {

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Insert
    void create(Client client);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Update
    void update(Client client);

    @StatementAttributes(consistencyLevel = "ONE", pageSize = 100)
    @Select
    Client findById(UUID clientId);

    @Delete
    void delete(Client client);

    @StatementAttributes(consistencyLevel = "ONE", pageSize = 100)
    @Query("SELECT * FROM bank_accounts.clients")
    PagingIterable<Client> findAll();
}
