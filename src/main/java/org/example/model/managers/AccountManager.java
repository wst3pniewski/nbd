package org.example.model.managers;

import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.transaction.Transactional;
import org.example.model.accounts.*;
import org.example.model.clients.Client;
import org.example.model.repositories.AccountRepository;
import org.example.model.repositories.Repository;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.Persistence.createEntityManagerFactory;

/*
 * Business logic for managing account
 * - It is not possible to delete an account -> only set it to INACTIVE
 * - It is not possible to set an account to INACTIVE if it has any balance or debit
 * - It is not possible to create an account for a client who exceeded the limit of accounts
 * - It is not possible to create an account with a negative balance (constructor fulfilled)
 * - It is not possible to create an account with a negative debit   (constructor fulfilled)
 * - It is not possible to create a junior account for a client who is not a child (constructor fulfilled)
 * - It is not possible to create any other account for a client who is a child (constructor fulfilled)
 */
public class AccountManager {
    private AccountRepository accountRepository;

//    @Transactional
    public BankAccount createStandardAccount(Client client, BigDecimal debitLimit) {
        BankAccount account = new StandardAccount(client, debitLimit);
        List<BankAccount> accounts = accountRepository.findByClientId(client.getId());
        if (accounts.size() >= client.getClientType().getMaxActiveAccounts()) {
            throw new IllegalArgumentException("Client exceeded the limit of accounts");
        }
        BankAccount bAccount = accountRepository.add(account);
        return bAccount;
    }

    public BankAccount createSavingAccount(Client client, BigDecimal interestRate) {
        BankAccount account = new SavingAccount(client, interestRate);
        List<BankAccount> accounts = accountRepository.findByClientId(client.getId());
        if (accounts.size() >= client.getClientType().getMaxActiveAccounts()) {
            throw new IllegalArgumentException("Client exceeded the limit of accounts");
        }
        return accountRepository.add(account);
    }

    public BankAccount createJuniorAccount(Client client, Client parent) {
        BankAccount account = new JuniorAccount(client, parent);
        List<BankAccount> accounts = accountRepository.findByClientId(client.getId());
        if (accounts.size() >= client.getClientType().getMaxActiveAccounts()) {
            throw new IllegalArgumentException("Client exceeded the limit of accounts");
        }
        return accountRepository.add(account);
    }

    public AccountManager(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
