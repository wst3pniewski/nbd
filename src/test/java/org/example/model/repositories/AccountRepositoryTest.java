package org.example.model.repositories;

import org.example.model.accounts.BankAccount;
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
        Address address = new Address("Ulica", "Lodz", "1");
        Client client = new Client("Add", "Account", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));

        //transaction
        accountRepository.add(account);
        //end

        BankAccount foundAccount = accountRepository.findById(account.getAccountId());

        assertEquals(account.getAccountId(), foundAccount.getAccountId());
    }

    @Test
    void findAll() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));
        //transaction
//        em.persist(client);
//        em.persist(account);
        //end

        List<BankAccount> bankAccountList = accountRepository.findAll();
        assert (bankAccountList.isEmpty() == false);
    }

    @Test
    void findById() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));
        //transaction
//        em.persist(client);
//        em.persist(account);
        //end

        BankAccount foundAccount = accountRepository.findById(account.getAccountId());

        assertEquals(account.getAccountId(), foundAccount.getAccountId());
    }

    @Test
    void updateAccount() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));
        //transaction
//        em.persist(client);
//        em.persist(account);
        //end

        account.setBalance(BigDecimal.valueOf(2000));
        //transaction
        BankAccount updatedAccount = accountRepository.update(account);
        //end
        BankAccount foundAccount = accountRepository.findById(account.getAccountId());
        assertEquals(BigDecimal.valueOf(2000), foundAccount.getBalance());
    }

    @Test
    void countActiveByClientId() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));
        //transaction
//        em.persist(client);
//        em.persist(account);
        //end
        long count = accountRepository.countActiveByClientId(client.getId());
        assertEquals(1, count);
    }

    @Test
    void getAccountsByClientId() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));
        //transaction;
//        em.persist(client);
//        em.persist(account);
        //end
        List<BankAccount> accounts = accountRepository.getAccountsByClientId(client.getId());
        assertEquals(1, accounts.size());
        assertEquals(account.getAccountId(), accounts.get(0).getAccountId());
    }

}