package org.example.model.repositories;

import org.example.model.RedisCache;
import org.example.model.accounts.BankAccount;
import org.example.model.accounts.JuniorAccount;
import org.example.model.accounts.SavingAccount;
import org.example.model.accounts.StandardAccount;
import org.example.model.clients.Client;
import org.example.model.managers.ClientManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CachedAccountRepositoryTest {
    private static CachedAccountRepository cachedAccountRepository;
    private static ClientManager clientManager;
    private static LocalDate dateOfBirth;

    private Client client;

    @BeforeAll
    static void beforeAll() {
        cachedAccountRepository = new CachedAccountRepository(new RedisCache(), new AccountRepository());
        clientManager = new ClientManager();
        dateOfBirth = LocalDate.of(2000, 1, 1);
    }

    @BeforeEach
    void beforeEach() {
        client = clientManager.createClient("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, "Street",
                "City", "42");
    }

    @Test
    void addAndFindStandardAccount() {
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));

        cachedAccountRepository.add(account);

        BankAccount foundAccount = cachedAccountRepository.findById(account.getId());

        assertEquals(account.getId(), foundAccount.getId());
    }

    @Test
    void addAndFindSavingsAccount() {
        BankAccount account = new SavingAccount(client, BigDecimal.valueOf(0.1));

        cachedAccountRepository.add(account);

        BankAccount foundAccount = cachedAccountRepository.findById(account.getId());

        assertEquals(account.getId(), foundAccount.getId());
    }

    @Test
    void addAndFindJuniorAccount() {
        Client child = clientManager.createClient("John", "Doe", LocalDate.of(2009, 1, 1), Client.ClientTypes.BUSINESS, "Street",
                "City", "42");
        BankAccount account = new JuniorAccount(child, client);

        cachedAccountRepository.add(account);

        BankAccount foundAccount = cachedAccountRepository.findById(account.getId());

        assertEquals(account.getId(), foundAccount.getId());
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }
}