package org.example.model.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Query;
import org.example.model.domain.accounts.SavingAccount;

@Dao
public interface SavingAccountDao extends BaseDao<SavingAccount> {
    @Query("SELECT * FROM bank_accounts.saving_accounts")
    PagingIterable<SavingAccount> findAll();
}
