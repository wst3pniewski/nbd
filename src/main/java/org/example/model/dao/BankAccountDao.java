package org.example.model.dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import org.example.model.BankAccountQueryProvider;
import org.example.model.domain.accounts.BankAccount;
import org.example.model.domain.accounts.JuniorAccount;
import org.example.model.domain.accounts.SavingAccount;
import org.example.model.domain.accounts.StandardAccount;

import java.util.List;
import java.util.UUID;

@Dao
public interface BankAccountDao {
    @StatementAttributes(consistencyLevel = "QUORUM")
    @QueryProvider(providerClass = BankAccountQueryProvider.class,
            entityHelpers = {JuniorAccount.class, SavingAccount.class, StandardAccount.class})
    BankAccount findById(UUID accountId);

    @StatementAttributes(consistencyLevel = "ONE", pageSize = 100)
    @QueryProvider(providerClass = BankAccountQueryProvider.class,
            entityHelpers = {JuniorAccount.class, SavingAccount.class, StandardAccount.class})
    List<BankAccount> findAll();

    @StatementAttributes(consistencyLevel = "QUORUM")
    @QueryProvider(providerClass = BankAccountQueryProvider.class,
            entityHelpers = {JuniorAccount.class, SavingAccount.class, StandardAccount.class})
    void create(BankAccount bankAccount);

    @Delete
    void delete(BankAccount bankAccount);
}
