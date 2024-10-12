package org.example.model.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.model.accounts.BankAccount;
import org.example.model.accounts.SavingAccount;
import org.example.model.accounts.StandardAccount;
import org.example.model.clients.Address;
import org.example.model.clients.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest {

    private static AccountRepository accountRepository;
    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_RENT_PU");
        em = emf.createEntityManager();
        accountRepository = new AccountRepository();
    }

    @AfterAll
    static void afterAll() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void addAccount() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Ulica", "Lodz", "1");
        Client client = new Client("Add", "Account", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(client);
        transaction.commit();
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));
        BankAccount addedAccount = accountRepository.addAccount(account);
        assertNotNull(addedAccount);
    }

    @Test
    void findAll() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(client);
        em.persist(account);
        transaction.commit();

        List<BankAccount> bankAccountList = accountRepository.findAll();
        assert (bankAccountList.isEmpty() == false);
    }

    @Test
    void findById() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(client);
        em.persist(account);
        transaction.commit();

        BankAccount foundAccount = accountRepository.findById(account.getAccountId());

        assertEquals(account.getAccountId(), foundAccount.getAccountId());
    }

    @Test
    void updateAccount() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(client);
        em.persist(account);
        transaction.commit();

        account.setBalance(BigDecimal.valueOf(2000));
        BankAccount updatedAccount = accountRepository.updateAccount(account);
        assertNotNull(updatedAccount);
    }

    @Test
    void deleteAccount() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(client);
        em.persist(account);
        transaction.commit();
        BankAccount deletedAccount = accountRepository.deleteAccount(account.getAccountId());
        assertEquals(account.getAccountId(), deletedAccount.getAccountId());
    }

    @Test
    void countClientActiveAccounts() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        BankAccount account1 = new StandardAccount(client, BigDecimal.valueOf(1000));
        BankAccount account2 = new SavingAccount(client, BigDecimal.valueOf(10_000));
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(client);
        em.persist(account1);
        em.persist(account2);
        transaction.commit();
        long accountsCount = accountRepository.countClientActiveAccounts(client.getId());
        assertEquals(2, accountsCount);
    }
}