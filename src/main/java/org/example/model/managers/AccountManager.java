package org.example.model.managers;

import jakarta.transaction.Transactional;
import org.example.model.accounts.BankAccount;
import org.example.model.accounts.JuniorAccount;
import org.example.model.accounts.SavingAccount;
import org.example.model.accounts.StandardAccount;
import org.example.model.clients.Client;
import org.example.model.repositories.AccountRepository;
import org.example.model.repositories.Repository;

import java.math.BigDecimal;

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

    @Transactional
    public BankAccount createStandardAccount(Client client, BigDecimal debitLimit) {
        BankAccount account = new StandardAccount(client, debitLimit);
        long accountCount = accountRepository.countClientActiveAccounts(client.getId());
        int maxacc = client.getClientType().getMaxActiveAccounts();
        if (accountCount >= client.getClientType().getMaxActiveAccounts()) {
            throw new IllegalStateException("Client cannot have more than " + client.getClientType().getMaxActiveAccounts() + " bank accounts.");
        }
        BankAccount bAccount = accountRepository.addAccount(account);
        return bAccount;
    }

    public BankAccount createSavingAccount(Client client, BigDecimal interestRate) {
        BankAccount account = new SavingAccount(client, interestRate);
        return accountRepository.addAccount(account);
    }

    public BankAccount createJuniorAccount(Client client, Client parent) {
        BankAccount account = new JuniorAccount(client, parent);
        return accountRepository.addAccount(account);
    }

    public AccountManager() {
        this.accountRepository = new AccountRepository();
    }
}
