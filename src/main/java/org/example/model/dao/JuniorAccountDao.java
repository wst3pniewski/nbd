package org.example.model.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Query;
import org.example.model.domain.accounts.JuniorAccount;

@Dao
public interface JuniorAccountDao extends BaseDao<JuniorAccount> {
    @Query("SELECT * FROM bank_accounts.junior_accounts")
    PagingIterable<JuniorAccount> findAll();
}
