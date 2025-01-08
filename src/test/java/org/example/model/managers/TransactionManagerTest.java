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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransactionManagerTest {
    private static TransactionManager transactionManager;
    private static AccountManager accountManager;
    private static ClientManager clientManager;
    private static Client client;
    private static Client child;
    private static Client parent;
    private static BankAccount account1;
    private static BankAccount account2;

    @BeforeAll
    static void beforeAll() {
        accountManager = new AccountManager();
        clientManager = new ClientManager();
        transactionManager = new TransactionManager();
    }

    @AfterAll
    static void afterAll() {
        accountManager.close();
        clientManager.close();
        transactionManager.close();
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

        account1 = accountManager.createStandardAccount(client.getClientId(), BigDecimal.valueOf(1000));
        account2 = accountManager.createStandardAccount(client.getClientId(), BigDecimal.valueOf(1000));

        LocalDate dateOfBirthChild = LocalDate.of(2010, 1, 1);
        child = clientManager.createClient(UUID.randomUUID(),
                Client.STANDARD,
                dateOfBirthChild,
                "John",
                "Doe Jr.",
                "8th Avenue",
                "NYC",
                "1");

        parent = clientManager.createClient(UUID.randomUUID(),
                Client.BUSINESS,
                dateOfBirth,
                "John",
                "Doe",
                "8th Avenue",
                "NYC",
                "1");
    }

    @Test
    void StandardAccountTransactionWithDebt() {
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));

        transactionManager.createStandardTransaction(account1.getId(), account2.getId(), BigDecimal.valueOf(1100));

        account1 = accountManager.findById(account1.getId());
        account2 = accountManager.findById(account2.getId());

        assertEquals(BigDecimal.valueOf(0), account1.getBalance());
        assertEquals(BigDecimal.valueOf(1000), ((StandardAccount) account1).getDebit());
        assertEquals(BigDecimal.valueOf(1100), account2.getBalance());
    }

    @Test
    void StandardAccountTransactionPositive() {
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));

        transactionManager.createStandardTransaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));

        account1 = accountManager.findById(account1.getId());
        account2 = accountManager.findById(account2.getId());

        assertEquals(BigDecimal.valueOf(0), account1.getBalance());
        assertEquals(BigDecimal.valueOf(0), ((StandardAccount) account1).getDebit());
        assertEquals(BigDecimal.valueOf(100), account2.getBalance());
    }

    @Test
    void StandardAccountTransactionInactiveAccount() {
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
        account1.setActive(false);
        accountManager.update(account1);
        assertThrows(IllegalArgumentException.class, () -> transactionManager
                .createStandardTransaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100)));
    }

    @Test
    void StandardOtherTypeAccountTransaction() {
        account2 = accountManager.createSavingAccount(client.getClientId(), BigDecimal.valueOf(0.1));
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
        transactionManager.createStandardTransaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));

        account1 = accountManager.findById(account1.getId());
        account2 = accountManager.findById(account2.getId());

        assertEquals(BigDecimal.valueOf(0), account1.getBalance());
        assertEquals(BigDecimal.valueOf(0), ((StandardAccount) account1).getDebit());
        assertEquals(BigDecimal.valueOf(100), account2.getBalance());
    }

    @Test
    void OtherToStandardAccountTransaction() {
        account2 = accountManager.createSavingAccount(client.getClientId(), BigDecimal.valueOf(0.1));
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
        assertThrows(IllegalArgumentException.class, () -> transactionManager
                .createStandardTransaction(account2.getId(), account1.getId(), BigDecimal.valueOf(100)));
    }

    @Test
    void StandardAccountTransactionBalanceExceeded() {
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
        assertThrows(IllegalArgumentException.class, () -> transactionManager
                .createStandardTransaction(account1.getId(), account2.getId(), BigDecimal.valueOf(10_000)));
    }

    @Test
    void StandardAccountTransactionNegativeAmount() {
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
        assertThrows(IllegalArgumentException.class, () -> transactionManager
                .createStandardTransaction(account1.getId(), account2.getId(), BigDecimal.valueOf(-10_000)));
    }

    @Test
    void SavingAccountTransactionPositive() {
        BankAccount account1 = accountManager.createSavingAccount(client.getClientId(), BigDecimal.valueOf(0.1));
        BankAccount account2 = accountManager.createSavingAccount(client.getClientId(), BigDecimal.valueOf(0.1));
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
        transactionManager.createJuniorOrSavingTransaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));

        account1 = accountManager.findById(account1.getId());
        account2 = accountManager.findById(account2.getId());

        assertEquals(BigDecimal.valueOf(0), account1.getBalance());
        assertEquals(BigDecimal.valueOf(100), account2.getBalance());
    }

    @Test
    void JuniorAccountTransactionPositive() {
        BankAccount account1 = accountManager.createJuniorAccount(child.getClientId(), parent.getClientId());
        BankAccount account2 = accountManager.createJuniorAccount(child.getClientId(), parent.getClientId());
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
        transactionManager.createJuniorOrSavingTransaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));

        account1 = accountManager.findById(account1.getId());
        account2 = accountManager.findById(account2.getId());

        assertEquals(BigDecimal.valueOf(0), account1.getBalance());
        assertEquals(BigDecimal.valueOf(100), account2.getBalance());
    }

    @Test
    void JuniorSavingAccountTransactionPositive() {
        account1 = accountManager.createJuniorAccount(child.getClientId(), parent.getClientId());
        account2 = accountManager.createSavingAccount(parent.getClientId(), BigDecimal.valueOf(0.1));
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
        transactionManager.createJuniorOrSavingTransaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));

        account1 = accountManager.findById(account1.getId());
        account2 = accountManager.findById(account2.getId());

        assertEquals(BigDecimal.valueOf(0), account1.getBalance());
        assertEquals(BigDecimal.valueOf(100), account2.getBalance());
    }

    @Test
    void JuniorSavingAccountTransactionInsufficientBalance() {
        BankAccount account1 = accountManager.createJuniorAccount(child.getClientId(), parent.getClientId());
        BankAccount account2 = accountManager.createSavingAccount(parent.getClientId(), BigDecimal.valueOf(0.1));
        assertThrows(IllegalArgumentException.class, () -> transactionManager
                .createJuniorOrSavingTransaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100)));
    }

    @Test
    void JuniorSavingAccountTransactionNegativeAmount() {
        BankAccount account1 = accountManager.createJuniorAccount(child.getClientId(), parent.getClientId());
        BankAccount account2 = accountManager.createSavingAccount(parent.getClientId(), BigDecimal.valueOf(0.1));
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
        assertThrows(IllegalArgumentException.class, () -> transactionManager
                .createJuniorOrSavingTransaction(account1.getId(), account2.getId(), BigDecimal.valueOf(-100)));
    }

    @Test
    void JuniorNotExistingAccountTransaction() {
        BankAccount account1 = accountManager.createJuniorAccount(child.getClientId(), parent.getClientId());
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
        assertThrows(IllegalArgumentException.class, () -> transactionManager
                .createJuniorOrSavingTransaction(account1.getId(), UUID.randomUUID(), BigDecimal.valueOf(100)));
    }

    @Test
    void JuniorStandardAccountTransaction() {
        BankAccount account1 = accountManager.createJuniorAccount(child.getClientId(), parent.getClientId());
        BankAccount account2 = accountManager.createStandardAccount(parent.getClientId(), BigDecimal.valueOf(1000));
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
        transactionManager.createJuniorOrSavingTransaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));

        account1 = accountManager.findById(account1.getId());
        account2 = accountManager.findById(account2.getId());

        assertEquals(BigDecimal.valueOf(0), account1.getBalance());
        assertEquals(BigDecimal.valueOf(100), account2.getBalance());
    }

    @Test
    void StandardJuniorAccountTransaction() {
        BankAccount account1 = accountManager.createJuniorAccount(child.getClientId(), parent.getClientId());
        BankAccount account2 = accountManager.createStandardAccount(parent.getClientId(), BigDecimal.valueOf(1000));
        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
        assertThrows(IllegalArgumentException.class, () -> transactionManager
                .createJuniorOrSavingTransaction(account2.getId(), account1.getId(), BigDecimal.valueOf(100)));
    }

    @Test
    void JuniorSavingAccountTransactionInActiveAccount() {
        BankAccount account1 = accountManager.createJuniorAccount(child.getClientId(), parent.getClientId());
        account1.setActive(false);
        accountManager.update(account1);
        BankAccount account2 = accountManager.createSavingAccount(parent.getClientId(), BigDecimal.valueOf(0.1));
        assertThrows(IllegalArgumentException.class, () -> transactionManager
                .createJuniorOrSavingTransaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100)));
    }

}