package org.example.model.managers;


import org.example.model.domain.accounts.*;
import org.example.model.domain.clients.Client;
import org.example.model.repositories.AccountRepository;
import org.example.model.repositories.ClientRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AccountManager {
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountManager() {
        this.accountRepository = new AccountRepository();
        this.clientRepository = new ClientRepository();
    }

    public BankAccount createStandardAccount(UUID clientId, BigDecimal debitLimit) {
        Client client = clientRepository.findById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }
        int clientAge = client.getDateOfBirth().until(LocalDate.now()).getYears();

        if (clientAge< 18) {
            throw new IllegalArgumentException("Parent is not an adult");
        }
//        int activeAccounts = client.getActiveAccounts();
//        if (activeAccounts >= client.getClientType().getMaxActiveAccounts()) {
//            throw new IllegalArgumentException("Client exceeded the limit of accounts");
//        }
//
//        client.setActiveAccounts((activeAccounts + 1));
//        clientRepository.update(client);

        BankAccount account = new StandardAccount(clientId, debitLimit);
        accountRepository.add(account);

        return account;
    }

    public BankAccount createSavingAccount(UUID clientId, BigDecimal interestRate) {

        Client client = clientRepository.findById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }

        int clientAge = client.getDateOfBirth().until(LocalDate.now()).getYears();

        if (clientAge< 18) {
            throw new IllegalArgumentException("Parent is not an adult");
        }
//        int activeAccounts = client.getActiveAccounts();
//        if (activeAccounts >= client.getClientType().getMaxActiveAccounts()) {
//            throw new IllegalArgumentException("Client exceeded the limit of accounts");
//        }
//
//        client.setActiveAccounts((activeAccounts + 1));
//        clientRepository.update(client);

        BankAccount account = new SavingAccount(clientId, interestRate);
        accountRepository.add(account);

        return account;
    }

    public BankAccount createJuniorAccount(UUID clientId, UUID parentId) {

        Client client = clientRepository.findById(clientId);

        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }

        int clientAge = client.getDateOfBirth().until(LocalDate.now()).getYears();

        if (clientAge >= 18) {
            throw new IllegalArgumentException("Client is not a child");
        }

        Client parent = clientRepository.findById(parentId);

        if (parent == null) {
            throw new IllegalArgumentException("Parent not found");
        }

        int parentAge = parent.getDateOfBirth().until(LocalDate.now()).getYears();
        if (parentAge < 18) {
            throw new IllegalArgumentException("Parent is not an adult");
        }


//        int activeAccounts = client.getActiveAccounts();
//        if (activeAccounts >= client.getClientType().getMaxActiveAccounts()) {
//            throw new IllegalArgumentException("Parent exceeded the limit of accounts");
//        }
//        client.setActiveAccounts((activeAccounts + 1));
//        clientRepository.update(client);

        BankAccount account = new JuniorAccount(clientId, parentId);
        accountRepository.add(account);

        return account;
    }


    public BankAccount closeAccount(UUID accountId) {
        BankAccount account = accountRepository.findById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }

//        Client client = clientRepository.findById(account.getClient().getId());
//        int activeAccounts = client.getActiveAccounts();

        account.setActive(false);
//        account.setCloseDate(LocalDate.now());
        accountRepository.update(account);
        return account;
    }

    public BankAccount depositMoney(UUID accountId, BigDecimal amount) {
        BankAccount account = accountRepository.findById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        account.setBalance(amount);
        accountRepository.update(account);

        return account;
    }

    public BankAccount withdrawMoney(UUID accountId, BigDecimal amount) {
        BankAccount account = accountRepository.findById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        BigDecimal balance = account.getBalance();
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        account.setBalance(balance.subtract(amount));
        accountRepository.update(account);

        return account;
    }

    public BankAccount interestCalculation(UUID accountId) {
        BankAccount account = accountRepository.findById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
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

    public BankAccount payDebt(UUID accountId, BigDecimal amount) {
        BankAccount account = accountRepository.findById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
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

    public BankAccount findById(UUID id) {
        return accountRepository.findById(id);
    }

    public List<BankAccount> findAll() {
        return accountRepository.findAll();
    }

    public void update(BankAccount bankAccount, BankAccount bankAccount2) {
        if (bankAccount == null || bankAccount2 == null) {
            return;
        }
        accountRepository.update(bankAccount, bankAccount2);
    }

    public void update(BankAccount account) {
        if (account == null) {
            return;
        }
        accountRepository.update(account);
    }

    public List<BankAccount> getAccountsByClientId(UUID clientId) {
        return accountRepository.findByClientId(clientId);
    }

    public long countActiveByClientId(UUID clientId) {
        return accountRepository.countActiveByClientId(clientId);
    }

    public void close() {
        accountRepository.close();
        clientRepository.close();
    }
}