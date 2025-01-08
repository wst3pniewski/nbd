package org.example.model.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.domain.clients.Client;

import java.util.UUID;

@Dao
public interface ClientDao {
    @Insert
    void create(Client client);
    @Update
    void update(Client client);

//    @Update(customWhereClause = "client_id = :id")
//    void update(Client client, UUID id);

    @Select
    Client findById(UUID clientId);

    @Delete
    void delete(Client client);

    @Query("SELECT * FROM bank_accounts.clients")
    PagingIterable<Client> findAll();
}
