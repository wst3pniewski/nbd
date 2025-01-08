package org.example.model.repositories;

import org.example.model.domain.Transaction;
import org.example.model.domain.accounts.BankAccount;
import org.example.model.domain.clients.Client;
import org.example.model.managers.AccountManager;
import org.example.model.managers.ClientManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryTest {
    private static TransactionRepository transactionRepository;
    private static ClientManager clientManager;
    private static AccountManager accountManager;
    private static Client client;
    private static BankAccount account1;
    private static BankAccount account2;
    @BeforeAll
    static void beforeAll() {
        transactionRepository = new TransactionRepository();
        clientManager = new ClientManager();
        accountManager = new AccountManager();
    }

    @AfterAll
    static void afterAll() {
        transactionRepository.close();
    }

    @BeforeEach
    void setUp() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        client = clientManager.createClient(UUID.randomUUID(),
                Client.BUSINESS,
                dateOfBirth,
                "John",
                "Doe",
                "8th Avenue",
                "NYC",
                "1");
        account1 = accountManager.createStandardAccount(client.getClientId(), BigDecimal.valueOf(1000));
        account2 = accountManager.createStandardAccount(client.getClientId(), BigDecimal.valueOf(1000));
    }
    @Test
    void add() {
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
        Transaction transaction = new Transaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));

        transactionRepository.add(transaction);

        Transaction foundTransaction = transactionRepository.findById(transaction.getId());

        assertEquals(transaction.getId(), foundTransaction.getId());
    }

    @Test
    void findAll() {
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));

        Transaction transaction = new Transaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));
        Transaction transaction2 = new Transaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));

        transactionRepository.add(transaction);
        transactionRepository.add(transaction2);

        List<Transaction> transactions = transactionRepository.findAll();
        assertFalse(transactions.isEmpty());
    }

    @Test
    void findById() {
        account1 = accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));

        Transaction transaction = new Transaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));

        transactionRepository.add(transaction);

        Transaction fountTransaction = transactionRepository.findById(transaction.getId());
        assertEquals(transaction.getId(), fountTransaction.getId());
    }

    @Test
    void deletePositive(){
        account1 = accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));

        Transaction transaction = new Transaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));

        transactionRepository.add(transaction);

        transactionRepository.delete(transaction.getId());
        assertNull(transactionRepository.findById(transaction.getId()));
    }
}