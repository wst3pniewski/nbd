package org.example.model.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.domain.clients.Client;

import java.util.UUID;

@Dao
public interface ClientDao {
    @Insert
    void create(Client client);

//    @Query("SELECT * FROM clients WHERE client_id = :clientId")
    @Select
    Client findById(UUID clientId);

    @Query("SELECT * FROM bank_accounts.clients")
    PagingIterable<Client> findAll();

    @Delete
    void delete(Client client);
}
