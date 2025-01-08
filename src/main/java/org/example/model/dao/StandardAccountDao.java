package org.example.model.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Query;
import org.example.model.domain.accounts.StandardAccount;

@Dao
public interface StandardAccountDao extends BaseDao<StandardAccount> {
    @Query("SELECT * FROM bank_accounts.standard_accounts")
    PagingIterable<StandardAccount> findAll();

//    @Query("SELECT * FROM bank_accounts.standard_accounts WHERE client_id = :clientId")
}
