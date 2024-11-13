package org.example.model.managers;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.TransactionBody;
import com.mongodb.session.ClientSession;
import org.example.model.MongoDBConnection;
import org.example.model.accounts.*;
import org.example.model.clients.Client;
import org.example.model.repositories.AbstractMongoRepository;
import org.example.model.repositories.AccountRepository;
import org.example.model.repositories.ClientRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AccountManager {
    private AccountRepository accountRepository;
    private ClientRepository clientRepository;
    private MongoClient mongoClient;
    private MongoDatabase database;


    public AccountManager() {
        this.accountRepository = new AccountRepository();
        this.clientRepository = new ClientRepository();

        this.mongoClient = MongoDBConnection.createMongoClient();
        this.database = mongoClient.getDatabase("bankSystemDB");
    }

    public BankAccount createStandardAccount(UUID clientId, BigDecimal debitLimit) {
        BankAccount account = null;

        Client client = clientRepository.findById(clientId);
        long activeAccounts = accountRepository.countActiveByClientId(clientId);
        if (activeAccounts >= client.getClientType().getMaxActiveAccounts()) {
            throw new IllegalArgumentException("Client exceeded the limit of accounts");
        }
        account = new StandardAccount(client, debitLimit);
        accountRepository.add(account);

        return account;
    }

    public BankAccount createSavingAccount(UUID clientId, BigDecimal interestRate) {
        BankAccount account = null;

        Client client = clientRepository.findById(clientId);
        long activeAccounts = accountRepository.countActiveByClientId(clientId);
        if (activeAccounts >= client.getClientType().getMaxActiveAccounts()) {
            throw new IllegalArgumentException("Client exceeded the limit of accounts");
        }
        account = new SavingAccount(client, interestRate);
        accountRepository.add(account);

        return account;
    }

    public BankAccount createJuniorAccount(UUID clientId, UUID parentId) {
//        BankAccount account;
        Client client = clientRepository.findById(clientId);
        int clientAge = client.getDateOfBirth().until(LocalDate.now()).getYears();

        if (clientAge >= 18) {
            throw new IllegalArgumentException("Client is not a child");
        }
        Client parent = clientRepository.findById(parentId);
        if (parent == null) {
            throw new IllegalArgumentException("Parent not found");
        }


        try (var session = mongoClient.startSession()) {
            TransactionBody<BankAccount> transactionBody = (TransactionBody<BankAccount>) () -> {
                long activeAccounts = accountRepository.countActiveByClientId(parent.getId());
                if (activeAccounts >= parent.getClientType().getMaxActiveAccounts()) {
                    throw new IllegalArgumentException("Parent exceeded the limit of accounts");
                }

                BankAccount account = new JuniorAccount(client, parent);

                accountRepository.add(account);

                return account;
            };

            return session.withTransaction(transactionBody);

        }
    }

    public BankAccount depositMoney(UUID accountId, BigDecimal amount) {
        BankAccount account = null;

        account = accountRepository.findById(accountId);
        account.setBalance(amount);
        accountRepository.update(account);

        return account;
    }

    public BankAccount withdrawMoney(UUID accountId, BigDecimal amount) {
        BankAccount account = null;

        account = accountRepository.findById(accountId);
        BigDecimal balance = account.getBalance();
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        account.setBalance(balance.subtract(amount));
        accountRepository.update(account);

        return account;
    }

    public BankAccount interestCalculation(UUID accountId) {
        BankAccount account = null;

        account = accountRepository.findById(accountId);
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
        BankAccount account = null;

        account = accountRepository.findById(accountId);
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

    public BankAccount update(BankAccount account) {
        return accountRepository.update(account);
    }

    public List<BankAccount> getAccountsByClientId(UUID clientId) {
        return accountRepository.getAccountsByClientId(clientId);
    }

    public long countActiveByClientId(UUID clientId) {
        return accountRepository.countActiveByClientId(clientId);
    }

}
