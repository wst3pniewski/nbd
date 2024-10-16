package org.example.model.managers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.model.accounts.BankAccount;
import org.example.model.accounts.StandardAccount;
import org.example.model.clients.Client;
import org.example.model.repositories.AccountRepository;
import org.example.model.repositories.ClientRepository;
import org.example.model.repositories.TransactionRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionManagerTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static TransactionManager transactionManager;
    private static AccountManager accountManager;
    private static ClientManager clientManager;


    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_RENT_PU");
        em = emf.createEntityManager();
        TransactionRepository transactionRepository = new TransactionRepository(emf);
        AccountRepository accountRepository = new AccountRepository(emf);
        ClientRepository clientRepository = new ClientRepository(emf);
        accountManager = new AccountManager(accountRepository, clientRepository, emf);
        clientManager = new ClientManager(clientRepository);
        transactionManager = new TransactionManager(transactionRepository, accountRepository, emf);
    }

    @AfterAll
    static void afterAll() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void standardAccountTransactionWithDebt() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);

        Client client = clientManager.createClient("Add", "Account", dateOfBirth,
                Client.ClientTypes.STANDARD, "Ulica", "Lodz", "1");
        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));

        transactionManager.createStandardTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(1100));

        account1 = accountManager.findById(account1.getAccountId());
        account2 = accountManager.findById(account2.getAccountId());

        assertEquals(BigDecimal.valueOf(0.0).setScale(2), account1.getBalance());
        assertEquals(BigDecimal.valueOf(1000.0).setScale(2), ((StandardAccount) account1).getDebit());
        assertEquals(BigDecimal.valueOf(1100.0).setScale(2), account2.getBalance());
    }

    @Test
    void standardAccountTransaction() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);

        Client client = clientManager.createClient("Add", "Account", dateOfBirth,
                Client.ClientTypes.STANDARD, "Ulica", "Lodz", "1");
        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));

        transactionManager.createStandardTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(100));

        account1 = accountManager.findById(account1.getAccountId());
        account2 = accountManager.findById(account2.getAccountId());

        assertEquals(BigDecimal.valueOf(0.0).setScale(2), account1.getBalance());
        assertEquals(BigDecimal.valueOf(0.0).setScale(2), ((StandardAccount) account1).getDebit());
        assertEquals(BigDecimal.valueOf(100.0).setScale(2), account2.getBalance());
    }

    @Test
    void standardAccountTransactionBalanceExceeded() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);

        Client client = clientManager.createClient("Add", "Account", dateOfBirth,
                Client.ClientTypes.STANDARD, "Ulica", "Lodz", "1");
        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
        assertThrows(IllegalArgumentException.class, () -> transactionManager
                .createStandardTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(10_000)));

    }

    @Test
    void standardAccountTransactionNegativeAmount() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);

        Client client = clientManager.createClient("Add", "Account", dateOfBirth,
                Client.ClientTypes.STANDARD, "Ulica", "Lodz", "1");
        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
        assertThrows(IllegalArgumentException.class, () -> transactionManager
                .createStandardTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(-10_000)));

    }
}