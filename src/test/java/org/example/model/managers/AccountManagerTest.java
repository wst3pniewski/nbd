package org.example.model.managers;

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

class AccountManagerTest {
    private static AccountManager accountManager;
    private static ClientManager clientManager;
    private static Client client;

    @BeforeAll
    static void beforeAll() {
        clientManager = new ClientManager();
        accountManager = new AccountManager();
    }

    @AfterAll
    static void afterAll() {

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
    }

//    TODO: Do I need this business logic?
//    @Test
//    void maxAccountsExceeded() {
//        accountManager.createStandardAccount(client.getClientId(), BigDecimal.valueOf(1000));
//        accountManager.createStandardAccount(client.getClientId(), BigDecimal.valueOf(1000));
//        long activeAccounts = accountManager.countActiveByClientId(client.getClientId());
//
//        assertThrows(IllegalArgumentException.class, () -> accountManager.createStandardAccount(client.getClientId(), BigDecimal.valueOf(1000)));
//        assertEquals(2, activeAccounts);
//    }

    @Test
    void createStandardAccount() {
        BankAccount bankAccount = accountManager.createStandardAccount(client.getClientId(), BigDecimal.valueOf(1000));
        List<BankAccount> activeAccountsL = accountManager.getAccountsByClientId(client.getClientId());


        assertEquals(1, activeAccountsL.size());
        assertEquals(bankAccount.getId(), activeAccountsL.getFirst().getId());
    }

    @Test
    void createSavingAccount() {
        BankAccount bankAccount = accountManager.createSavingAccount(client.getClientId(), BigDecimal.valueOf(0.1));
        List<BankAccount> activeAccountsL = accountManager.getAccountsByClientId(client.getClientId());

        assertEquals(1, activeAccountsL.size());
        assertEquals(bankAccount.getId(), activeAccountsL.getFirst().getId());
    }

    @Test
    void createJuniorAccount() {
        LocalDate dateOfBirth = LocalDate.of(2010, 1, 1);
        Client juniorClient = clientManager.createClient(UUID.randomUUID(),
                Client.STANDARD,
                dateOfBirth,
                "John",
                "Doe Jr.",
                "8th Avenue",
                "NYC",
                "1");
        BankAccount bankAccount = accountManager.createJuniorAccount(juniorClient.getClientId(), client.getClientId());
        List<BankAccount> activeAccountsL = accountManager.getAccountsByClientId(juniorClient.getClientId());

        assertEquals(1, activeAccountsL.size());
        assertEquals(bankAccount.getId(), activeAccountsL.getFirst().getId());
    }

    @Test
    void createJuniorAccountAgeExceeded() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Client juniorClient = clientManager.createClient(UUID.randomUUID(),
                Client.STANDARD,
                dateOfBirth,
                "John",
                "Doe Jr.",
                "8th Avenue",
                "NYC",
                "1");
        assertThrows(IllegalArgumentException.class, () -> accountManager.createJuniorAccount(juniorClient.getClientId(), client.getClientId()));
    }

    @Test
    void createStandardAccountAgeUnder18() {
        LocalDate dateOfBirth = LocalDate.of(2010, 1, 1);
        client = clientManager.createClient(UUID.randomUUID(),
                Client.BUSINESS,
                dateOfBirth,
                "John",
                "Doe",
                "8th Avenue",
                "NYC",
                "1");
        assertThrows(IllegalArgumentException.class, () -> accountManager.createStandardAccount(client.getClientId(), BigDecimal.valueOf(1000)));
    }

    @Test
    void createSavingAccountAgeUnder18() {
        LocalDate dateOfBirth = LocalDate.of(2010, 1, 1);
        client = clientManager.createClient(UUID.randomUUID(),
                Client.BUSINESS,
                dateOfBirth,
                "John",
                "Doe",
                "8th Avenue",
                "NYC",
                "1");
        assertThrows(IllegalArgumentException.class, () -> accountManager.createStandardAccount(client.getClientId(), BigDecimal.valueOf(0.1)));
    }

    @Test
    void payDebt() {
        BankAccount bankAccount = accountManager.createStandardAccount(client.getClientId(), BigDecimal.valueOf(1000));
        bankAccount.setBalance(BigDecimal.valueOf(1000));
        ((StandardAccount) bankAccount).setDebit(BigDecimal.valueOf(500));
        accountManager.update(bankAccount);
        accountManager.payDebt(bankAccount.getId(), BigDecimal.valueOf(500));
        BankAccount updatedAccount = accountManager.findById(bankAccount.getId());
        assertEquals(BigDecimal.valueOf(0), ((StandardAccount) updatedAccount).getDebit());
    }

    @Test
    void calculateAndAddInterest() {
        BankAccount bankAccount = accountManager.createSavingAccount(client.getClientId(), BigDecimal.valueOf(0.1));
        bankAccount.setBalance(BigDecimal.valueOf(1000));
        accountManager.update(bankAccount);
        accountManager.interestCalculation(bankAccount.getId());
        assertEquals(BigDecimal.valueOf(1100.0), accountManager.findById(bankAccount.getId()).getBalance());
    }

    @Test
    void depositMoney() {
        BankAccount bankAccount = accountManager.createStandardAccount(client.getClientId(), BigDecimal.valueOf(1000));
        accountManager.depositMoney(bankAccount.getId(), BigDecimal.valueOf(500));
        BankAccount updatedAccount = accountManager.findById(bankAccount.getId());
        assertEquals(BigDecimal.valueOf(500), updatedAccount.getBalance());
    }

    @Test
    void withdrawMoney() {
        BankAccount bankAccount = accountManager.createStandardAccount(client.getClientId(), BigDecimal.valueOf(1000));
        accountManager.depositMoney(bankAccount.getId(), BigDecimal.valueOf(500));
        accountManager.withdrawMoney(bankAccount.getId(), BigDecimal.valueOf(500));
        BankAccount updatedAccount = accountManager.findById(bankAccount.getId());
        assertEquals(BigDecimal.valueOf(0), updatedAccount.getBalance());
    }

    @Test
    void withdrawMoneyOverBalance() {
        BankAccount bankAccount = accountManager.createStandardAccount(client.getClientId(), BigDecimal.valueOf(1000));
        accountManager.depositMoney(bankAccount.getId(), BigDecimal.valueOf(500));
        assertThrows(IllegalArgumentException.class, () -> accountManager.withdrawMoney(bankAccount.getId(), BigDecimal.valueOf(1000)));
    }
}