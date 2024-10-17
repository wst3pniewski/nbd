package org.example.model.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.Transaction;
import org.example.model.accounts.BankAccount;
import org.example.model.clients.Client;
import org.example.model.managers.AccountManager;
import org.example.model.managers.ClientManager;
import org.example.model.managers.TransactionManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static TransactionRepository transactionRepository;
    private static TransactionManager transactionManager;
    private static ClientManager clientManager;
    private static AccountManager accountManager;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_RENT_PU");
        em = emf.createEntityManager();
        transactionRepository = new TransactionRepository(em);
        clientManager = new ClientManager(em);
        accountManager = new AccountManager(em);
    }

    @AfterAll
    static void afterAll() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void add() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);

        Client client = clientManager.createClient("Add", "Account", dateOfBirth,
                Client.ClientTypes.STANDARD, "Ulica", "Lodz", "1");
        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
        Transaction transaction = new Transaction(account1, account2, BigDecimal.valueOf(100));
        em.getTransaction().begin();
        transactionRepository.add(transaction);
        em.getTransaction().commit();
        Transaction fountTransaction = transactionRepository.findById(transaction.getId());

        assertEquals(transaction.getId(), fountTransaction.getId());
    }

    @Test
    void findAll() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Client client = clientManager.createClient("Add", "Account", dateOfBirth,
                Client.ClientTypes.STANDARD, "Ulica", "Lodz", "1");

        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));

        Transaction transaction = new Transaction(account1, account2, BigDecimal.valueOf(100));
        Transaction transaction2 = new Transaction(account1, account2, BigDecimal.valueOf(100));
        em.getTransaction().begin();
        transactionRepository.add(transaction);
        transactionRepository.add(transaction2);
        em.getTransaction().commit();

        List<Transaction> transactions = transactionRepository.findAll();
        assertTrue(transactions.size() > 0);
    }

    @Test
    void findById() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Client client = clientManager.createClient("Add", "Account", dateOfBirth,
                Client.ClientTypes.STANDARD, "Ulica", "Lodz", "1");

        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));

        Transaction transaction = new Transaction(account1, account2, BigDecimal.valueOf(100));
        em.getTransaction().begin();
        transactionRepository.add(transaction);
        em.getTransaction().commit();

        Transaction fountTransaction = transactionRepository.findById(transaction.getId());
        assertEquals(transaction.getId(), fountTransaction.getId());
    }

}