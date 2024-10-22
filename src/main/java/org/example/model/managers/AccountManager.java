package org.example.model.managers;


import org.example.model.accounts.*;
import org.example.model.clients.Client;
import org.example.model.repositories.AccountRepository;
import org.example.model.repositories.ClientRepository;

import java.math.BigDecimal;
import java.util.List;

public class AccountManager {
    private AccountRepository accountRepository;
    private ClientRepository clientRepository;


    public AccountManager() {
    }

    public BankAccount createStandardAccount(long clientId, BigDecimal debitLimit) {
        BankAccount account = null;

        Client client = clientRepository.findByIdWithOptimisticLock(clientId);
        long activeAccounts = accountRepository.countActiveByClientId(clientId);
        if (activeAccounts >= client.getClientType().getMaxActiveAccounts()) {
            throw new IllegalArgumentException("Client exceeded the limit of accounts");
        }
        account = new StandardAccount(client, debitLimit);
        accountRepository.add(account);

        return account;
    }

    public BankAccount createSavingAccount(long clientId, BigDecimal interestRate) {
        BankAccount account = null;

        Client client = clientRepository.findByIdWithOptimisticLock(clientId);
        long activeAccounts = accountRepository.countActiveByClientId(clientId);
        if (activeAccounts >= client.getClientType().getMaxActiveAccounts()) {
            throw new IllegalArgumentException("Client exceeded the limit of accounts");
        }
        account = new SavingAccount(client, interestRate);
        accountRepository.add(account);

        return account;
    }

    public BankAccount createJuniorAccount(long clientId, long parentId) {
        BankAccount account = null;

        Client client = clientRepository.findByIdWithOptimisticLock(clientId);
        int clientAge = client.getDateOfBirth().until(client.getDateOfBirth()).getYears();

        if (clientAge >= 18) {
            throw new IllegalArgumentException("Client is not a child");
        }

        Client parent = clientRepository.findById(parentId);
        long activeAccounts = accountRepository.countActiveByClientId(client.getId());
        if (activeAccounts >= client.getClientType().getMaxActiveAccounts()) {
            throw new IllegalArgumentException("Client exceeded the limit of accounts");
        }

        account = new JuniorAccount(client, parent);
        accountRepository.add(account);

        return account;
    }

    public BankAccount depositMoney(Long accountId, BigDecimal amount) {
        BankAccount account = null;

        account = accountRepository.findByIdWithOptimisticLock(accountId);
        account.setBalance(amount);
        accountRepository.update(account);

        return account;
    }

    public BankAccount withdrawMoney(Long accountId, BigDecimal amount) {
        BankAccount account = null;

        account = accountRepository.findByIdWithOptimisticLock(accountId);
        BigDecimal balance = account.getBalance();
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        account.setBalance(balance.subtract(amount));
        accountRepository.update(account);

        return account;
    }

    public BankAccount interestCalculation(Long accountId) {
        BankAccount account = null;

        account = accountRepository.findByIdWithOptimisticLock(accountId);
        if (account instanceof SavingAccount) {
            BigDecimal interestRate = ((SavingAccount) account).getInterestRate();
            BigDecimal balance = account.getBalance();
            BigDecimal interest = balance.multiply(interestRate);
            account.setBalance(balance.add(interest));
            accountRepository.update(account);
        } else {
            throw new IllegalArgumentException("Account is not a saving account");
        }


        return account;
    }

    public BankAccount payDebt(Long accountId, BigDecimal amount) {
        BankAccount account = null;

        account = accountRepository.findByIdWithOptimisticLock(accountId);
        if (account instanceof StandardAccount) {
            BigDecimal debit = ((StandardAccount) account).getDebit();
            BigDecimal amountDebitSubtract = amount.subtract(debit);
            if (amountDebitSubtract.compareTo(BigDecimal.ZERO) >= 0) {
                account.setBalance(account.getBalance().add(amountDebitSubtract));
                ((StandardAccount) account).setDebit(BigDecimal.ZERO);
            } else {
                ((StandardAccount) account).setDebit(debit.subtract(amount));
            }
            accountRepository.update(account);
        } else {

            throw new IllegalArgumentException("Account is not a standard account");
        }

        return account;
    }

    public BankAccount findById(Long id) {
        return accountRepository.findById(id);
    }

    public List<BankAccount> findAll() {
        return accountRepository.findAll();
    }

    public BankAccount update(BankAccount account) {
        return accountRepository.update(account);
    }

    public BankAccount findByIdWithOptimisticLock(Long id) {
        return accountRepository.findByIdWithOptimisticLock(id);
    }

    public List<BankAccount> getAccountsByClientId(Long clientId) {
        return accountRepository.getAccountsByClientId(clientId);
    }

    public long countActiveByClientId(Long clientId) {
        return accountRepository.countActiveByClientId(clientId);
    }

}
