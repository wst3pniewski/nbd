package org.example.model.repositories;

import org.example.model.RedisCache;
import org.example.model.Transaction;
import org.example.model.accounts.BankAccount;
import org.example.model.clients.Client;
import org.example.model.managers.AccountManager;
import org.example.model.managers.ClientManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CachedTransactionRepositoryTest {
    private static CachedTransactionRepository cachedTransactionRepository;
    private static ClientManager clientManager;
    private static AccountManager accountManager;
    private static LocalDate dateOfBirth;

    private Client client;
    private BankAccount account1;
    private BankAccount account2;

    @BeforeAll
    static void beforeAll() {
        cachedTransactionRepository = new CachedTransactionRepository(new RedisCache(), new TransactionRepository());
        clientManager = new ClientManager();
        accountManager = new AccountManager();
        dateOfBirth = LocalDate.of(2000, 1, 1);
    }

    @BeforeEach
    void beforeEach() {
        client = clientManager.createClient("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, "Street",
                "City", "42");
        account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
    }

    @Test
    void addAndFind() {
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
        Transaction transaction = new Transaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));

        cachedTransactionRepository.add(transaction);

        Transaction foundTransaction = cachedTransactionRepository.findById(transaction.getId());

        assertEquals(transaction.getId(), foundTransaction.getId());
    }


    @Test
    void deletePositive() {
        account1 = accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));

        Transaction transaction = new Transaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));

        cachedTransactionRepository.add(transaction);

        assertTrue(cachedTransactionRepository.delete(transaction.getId()));
        assertNull(cachedTransactionRepository.findById(transaction.getId()));
    }

    @Test
    void deleteNegative(){
        account1 = accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));

        Transaction transaction = new Transaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));


        assertFalse(cachedTransactionRepository.delete(transaction.getId()));
        assertNull(cachedTransactionRepository.findById(transaction.getId()));
    }


}