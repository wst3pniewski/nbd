package org.example.model.repositories;

import org.example.model.accounts.BankAccount;
import org.example.model.accounts.SavingAccount;
import org.example.model.accounts.StandardAccount;
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

    @BeforeAll
    static void beforeAll() {
        accountRepository = new AccountRepository();
    }

    @AfterAll
    static void afterAll() {
    }

    @Test
    void addAccount() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
//        Address address = new Address("Ulica", "Lodz", "1");
        Client client = new Client("Add", "Account", dateOfBirth, Client.ClientTypes.BUSINESS, "Ulica", "Lodz", "1");
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));

        accountRepository.add(account);

        BankAccount foundAccount = accountRepository.findById(account.getId());

        assertEquals(account.getId(), foundAccount.getId());
    }

    @Test
    void findAll() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, "Aleja", "Lodz", "1");
        BankAccount account = new SavingAccount(client, BigDecimal.valueOf(1000));
        BankAccount account2 = new SavingAccount(client, BigDecimal.valueOf(0.1));
        accountRepository.add(account);
        accountRepository.add(account2);
        List<BankAccount> bankAccountList = accountRepository.findAll();
        assert (!bankAccountList.isEmpty());
    }

    @Test
    void findById() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, "Aleja", "Lodz", "1");
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));

        accountRepository.add(account);

        BankAccount foundAccount = accountRepository.findById(account.getId());

        assertEquals(account.getId(), foundAccount.getId());
    }

    @Test
    void updateAccount() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, "Aleja", "Lodz", "1");
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));

        accountRepository.add(account);

        account.setBalance(BigDecimal.valueOf(2000));
        account.setActive(false);
        accountRepository.update(account);

        BankAccount foundAccount = accountRepository.findById(account.getId());
        assertEquals(BigDecimal.valueOf(2000), foundAccount.getBalance());
    }

    @Test
    void countActiveByClientId() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, "Aleja", "Lodz", "1");
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));

        accountRepository.add(account);

        BankAccount account2 = new StandardAccount(client, BigDecimal.valueOf(1000));
        account2.setActive(false);

        accountRepository.add(account2);

        long count = accountRepository.countActiveByClientId(client.getId());
        assertEquals(1, count);
    }

    @Test
    void getAccountsByClientId() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, "Aleja", "Lodz", "1");
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));

        accountRepository.add(account);

        List<BankAccount> accounts = accountRepository.getAccountsByClientId(client.getId());
        assertEquals(1, accounts.size());
        assertEquals(account.getId(), accounts.getFirst().getId());
    }

}