package org.example.model.repositories;


import org.example.model.domain.accounts.BankAccount;
import org.example.model.domain.accounts.StandardAccount;
import org.example.model.domain.clients.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest {

    private static AccountRepository accountRepository;
    private static ClientRepository clientRepository;
    private Client client;

    @BeforeAll
    static void beforeAll() {
        accountRepository = new AccountRepository();
        clientRepository = new ClientRepository();
    }

    @AfterAll
    static void afterAll() {
        accountRepository.close();
        clientRepository.close();
    }

    @BeforeEach
    void setUp() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        client = new Client(UUID.randomUUID(),
                dateOfBirth,
                "John",
                "Doe",
                Client.BUSINESS,
                "8th Avenue",
                "NYC",
                "1");

        clientRepository.add(client);
    }

    @Test
    void addAndFindById() {
        BankAccount account = new StandardAccount(client.getClientId(), BigDecimal.valueOf(1000));

        accountRepository.add(account);

        BankAccount foundAccount = accountRepository.findById(account.getId());

        assertEquals(account.getId(), foundAccount.getId());
    }

    @Test
    void findAll() {
        BankAccount account1 = new StandardAccount(client.getClientId(), BigDecimal.valueOf(1000));
        BankAccount account2 = new StandardAccount(client.getClientId(), BigDecimal.valueOf(0.1));
        accountRepository.add(account1);
        accountRepository.add(account2);
        List<BankAccount> bankAccountList = accountRepository.findAll();
        assert (!bankAccountList.isEmpty());
    }

    @Test
    void findByClientId() {
        BankAccount account1 = new StandardAccount(client.getClientId(), BigDecimal.valueOf(1000));
        BankAccount account2 = new StandardAccount(client.getClientId(), BigDecimal.valueOf(0.1));
        accountRepository.add(account1);
        accountRepository.add(account2);
        List<BankAccount> bankAccountList = accountRepository.findByClientId(client.getClientId());
        assert (!bankAccountList.isEmpty());
        assert (bankAccountList.size() == 2);
    }

    @Test
    void updateAccount() {
        BankAccount account = new StandardAccount(client.getClientId(), BigDecimal.valueOf(1000));

        accountRepository.add(account);

        account.setBalance(BigDecimal.valueOf(2000));
        ((StandardAccount) account).setDebit(BigDecimal.valueOf(1000));
        account.setActive(false);
        accountRepository.update(account);

        BankAccount foundAccount = accountRepository.findById(account.getId());
        assertEquals(BigDecimal.valueOf(2000), foundAccount.getBalance());
    }

    @Test
    void countAccountsByClientId() {
        BankAccount account = new StandardAccount(client.getClientId(), BigDecimal.valueOf(1000));

        accountRepository.add(account);

        BankAccount account2 = new StandardAccount(client.getClientId(), BigDecimal.valueOf(1000));
        account2.setActive(false);

        accountRepository.add(account2);

        long count = accountRepository.countAccountsByClientId(client.getClientId());
        assertEquals(2, count);
    }
}