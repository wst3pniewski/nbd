package org.example.model.managers;

import jakarta.persistence.*;
import org.example.model.accounts.BankAccount;
import org.example.model.clients.Client;
import org.example.model.repositories.AccountRepository;
import org.example.model.repositories.ClientRepository;
import org.example.model.repositories.Repository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountManagerTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static AccountManager accountManager;
    private static ClientManager clientManager;


    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_RENT_PU");
        em = emf.createEntityManager();
        ClientRepository clientRepository = new ClientRepository(emf);
        AccountRepository accountRepository = new AccountRepository(emf);
        clientManager = new ClientManager(clientRepository);
        accountManager = new AccountManager(accountRepository, clientRepository, emf);
    }

    @AfterAll
    static void afterAll() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void maxAccountsExceeded() {
        EntityTransaction transaction = em.getTransaction();
        Client client = clientManager.createClient("Max", "TwoAccounts",
                LocalDate.of(2000, 1, 1),
                Client.ClientTypes.STANDARD, "street", "city", "1");
        transaction.begin();

        accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        assertThrows(IllegalArgumentException.class, () -> accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000)));
        long activeAccounts = accountManager.countActiveByClientId(client.getId());
        transaction.commit();

        assertEquals(2, activeAccounts);
    }

    @Test
    void createStandardAccount() {
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Client client = clientManager.createClient("John", "Doe",
                LocalDate.of(2000, 1, 1),
                Client.ClientTypes.STANDARD, "street", "city", "1");
        BankAccount bankAccount = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        List<BankAccount> activeAccountsL = accountManager.getAccountsByClientId(client.getId());
        transaction.commit();

        assertEquals(1, activeAccountsL.size());
        assertEquals(bankAccount.getAccountId(), activeAccountsL.getFirst().getAccountId());

    }

    @Test
    void createSavingAccount() {
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Client client = clientManager.createClient("Saving", "Account",
                LocalDate.of(2000, 1, 1),
                Client.ClientTypes.STANDARD, "street", "city", "1");
        BankAccount bankAccount = accountManager.createSavingAccount(client.getId(), BigDecimal.valueOf(0.1));
        List<BankAccount> activeAccountsL = accountManager.getAccountsByClientId(client.getId());
        transaction.commit();

        assertEquals(1, activeAccountsL.size());
        assertEquals(bankAccount.getAccountId(), activeAccountsL.getFirst().getAccountId());
    }

    @Test
    void createJuniorAccount() {
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Client client = clientManager.createClient("Joe", "Junior",
                LocalDate.of(2010, 1, 1),
                Client.ClientTypes.STANDARD, "street", "city", "1");
        Client parent = clientManager.createClient("John", "Doe",
                LocalDate.of(1980, 1, 1),
                Client.ClientTypes.STANDARD, "street", "city", "1");
        BankAccount bankAccount = accountManager.createJuniorAccount(client.getId(), parent.getId());
        List<BankAccount> activeAccountsL = accountManager.getAccountsByClientId(client.getId());
        transaction.commit();

        assertEquals(1, activeAccountsL.size());
        assertEquals(bankAccount.getAccountId(), activeAccountsL.getFirst().getAccountId());
    }

    @Test
    void createJuniorAccountAgeExceeded() {
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Client client = clientManager.createClient("Joe", "Junior",
                LocalDate.of(2000, 1, 1),
                Client.ClientTypes.STANDARD, "street", "city", "1");
        Client parent = clientManager.createClient("John", "Doe",
                LocalDate.of(1980, 1, 1),
                Client.ClientTypes.STANDARD, "street", "city", "1");
        assertThrows(IllegalArgumentException.class, () -> accountManager.createJuniorAccount(client.getId(), parent.getId()));
        transaction.commit();
    }

    @Test
    void createStandardAccountOptimisticLockException() {
//        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
//        ClientRepository clientRepository = new ClientRepository(em);
//        ClientManager clientManager = new ClientManager(clientRepository);
//        AccountRepository accountRepository = new AccountRepository(em);
//        AccountManager accountManager = new AccountManager(accountRepository, clientRepository);
//        EntityManagerFactory emfSecond = Persistence.createEntityManagerFactory("POSTGRES_RENT_PU2");
//        EntityManager emSecond = emfSecond.createEntityManager();
//
//        ClientRepository clientRepository2 = new ClientRepository(emSecond);
//        AccountRepository accountRepository2 = new AccountRepository(emSecond);
//        AccountManager accountManager2 = new AccountManager(accountRepository2, clientRepository2);
//
//        EntityTransaction transactionFirst = em.getTransaction();
//        EntityTransaction transactionSecond = emSecond.getTransaction();
//        transactionFirst.begin();
//        transactionSecond.begin();
//        Client client1 = clientManager.createClient("Add", "Account", dateOfBirth,
//                Client.ClientTypes.STANDARD, "Ulica", "Lodz", "1");
//        accountManager.createStandardAccount(client1, BigDecimal.valueOf(1000));
//        accountManager.createStandardAccount(client1, BigDecimal.valueOf(1000));
//        assertThrows(OptimisticLockException.class, () -> {
//            accountManager2.createStandardAccount(client1, BigDecimal.valueOf(1000));
//        });
//        transactionFirst.commit();
    }

}