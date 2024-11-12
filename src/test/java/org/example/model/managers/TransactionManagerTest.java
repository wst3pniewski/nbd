//package org.example.model.managers;
//
//
//import org.example.model.accounts.BankAccount;
//import org.example.model.accounts.StandardAccount;
//import org.example.model.clients.Client;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class TransactionManagerTest {
//    private static TransactionManager transactionManager;
//    private static AccountManager accountManager;
//    private static ClientManager clientManager;
//
//
//    @BeforeAll
//    static void beforeAll() {
//        accountManager = new AccountManager();
//        clientManager = new ClientManager();
//        transactionManager = new TransactionManager();
//    }
//
//    @AfterAll
//    static void afterAll() {
//    }
//
//    @Test
//    void StandardAccountTransactionWithDebt() {
////        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
////
////        Client client = clientManager.createClient("John", "Doe", dateOfBirth,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
////        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
////        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
////
////        transactionManager.createStandardTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(1100));
////
////        account1 = accountManager.findById(account1.getAccountId());
////        account2 = accountManager.findById(account2.getAccountId());
////
////        assertEquals(BigDecimal.valueOf(0), account1.getBalance());
////        assertEquals(BigDecimal.valueOf(1000), ((StandardAccount) account1).getDebit());
////        assertEquals(BigDecimal.valueOf(1100), account2.getBalance());
//    }
//
//    @Test
//    void StandardAccountTransactionPositive() {
////        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
////
////        Client client = clientManager.createClient("John", "Doe", dateOfBirth,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
////        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
////        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
////
////        transactionManager.createStandardTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(100));
////
////        account1 = accountManager.findById(account1.getAccountId());
////        account2 = accountManager.findById(account2.getAccountId());
////
////        assertEquals(BigDecimal.valueOf(0), account1.getBalance());
////        assertEquals(BigDecimal.valueOf(0), ((StandardAccount) account1).getDebit());
////        assertEquals(BigDecimal.valueOf(100), account2.getBalance());
//    }
//
//    @Test
//    void StandardAccountTransactionInActiveAccount() {
////        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
////
////        Client client = clientManager.createClient("John", "Doe", dateOfBirth,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
////        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
////        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
////        account1.setActive(false);
////        accountManager.update(account1);
////        assertThrows(IllegalArgumentException.class, () -> transactionManager
////                .createStandardTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(100)));
//    }
//
//    @Test
//    void StandardOtherTypeAccountTransaction() {
////        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
////
////        Client client = clientManager.createClient("John", "Doe", dateOfBirth,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
////        BankAccount account2 = accountManager.createSavingAccount(client.getId(), BigDecimal.valueOf(0.1));
////        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
////        transactionManager.createStandardTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(100));
////
////        assertEquals(BigDecimal.valueOf(0), account1.getBalance());
////        assertEquals(BigDecimal.valueOf(0), ((StandardAccount) account1).getDebit());
////        assertEquals(BigDecimal.valueOf(100), account2.getBalance());
//    }
//
//    @Test
//    void OtherToStandardAccountTransaction() {
////        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
////
////        Client client = clientManager.createClient("John", "Doe", dateOfBirth,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
////        BankAccount account2 = accountManager.createSavingAccount(client.getId(), BigDecimal.valueOf(0.1));
////        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
////        assertThrows(IllegalArgumentException.class, () -> transactionManager
////                .createStandardTransaction(account2.getAccountId(), account1.getAccountId(), BigDecimal.valueOf(100)));
//    }
//
//    @Test
//    void StandardAccountTransactionBalanceExceeded() {
////        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
////
////        Client client = clientManager.createClient("John", "Doe", dateOfBirth,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
////        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
////        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
////        assertThrows(IllegalArgumentException.class, () -> transactionManager
////                .createStandardTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(10_000)));
//
//    }
//
//    @Test
//    void StandardAccountTransactionNegativeAmount() {
////        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
////
////        Client client = clientManager.createClient("John", "Doe", dateOfBirth,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
////        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
////        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
////        assertThrows(IllegalArgumentException.class, () -> transactionManager
////                .createStandardTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(-10_000)));
//
//    }
//
//    @Test
//    void SavingAccountTransactionPositive() {
////        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
////
////        Client client = clientManager.createClient("John", "Doe", dateOfBirth,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createSavingAccount(client.getId(), BigDecimal.valueOf(0.1));
////        BankAccount account2 = accountManager.createSavingAccount(client.getId(), BigDecimal.valueOf(0.1));
////        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
////        transactionManager.createJuniorOrSavingTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(100));
////        assertEquals(BigDecimal.valueOf(0), account1.getBalance());
////        assertEquals(BigDecimal.valueOf(100), account2.getBalance());
//    }
//
//    @Test
//    void JuniorAccountTransactionPositive() {
////        LocalDate dateOfBirthChild = LocalDate.of(2010, 1, 1);
////        LocalDate dateOfBirthParent = LocalDate.of(1990, 1, 1);
////
////        Client child = clientManager.createClient("Child", "Child", dateOfBirthChild,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        Client parent = clientManager.createClient("Parent", "Parent", dateOfBirthParent,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createJuniorAccount(child.getId(), parent.getId());
////        BankAccount account2 = accountManager.createJuniorAccount(child.getId(), parent.getId());
////        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
////        transactionManager.createJuniorOrSavingTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(100));
////        assertEquals(BigDecimal.valueOf(0), account1.getBalance());
////        assertEquals(BigDecimal.valueOf(100), account2.getBalance());
//    }
//
//    @Test
//    void JuniorSavingAccountTransactionPositive() {
////        LocalDate dateOfBirthChild = LocalDate.of(2010, 1, 1);
////        LocalDate dateOfBirthParent = LocalDate.of(1990, 1, 1);
////
////        Client child = clientManager.createClient("Child", "Child", dateOfBirthChild,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        Client parent = clientManager.createClient("Parent", "Parent", dateOfBirthParent,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createJuniorAccount(child.getId(), parent.getId());
////        BankAccount account2 = accountManager.createSavingAccount(parent.getId(), BigDecimal.valueOf(0.1));
////        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
////        transactionManager.createJuniorOrSavingTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(100));
////        assertEquals(BigDecimal.valueOf(0), account1.getBalance());
////        assertEquals(BigDecimal.valueOf(100), account2.getBalance());
//    }
//
//    @Test
//    void JuniorSavingAccountTransactionInsufficientBalance() {
////        LocalDate dateOfBirthChild = LocalDate.of(2010, 1, 1);
////        LocalDate dateOfBirthParent = LocalDate.of(1990, 1, 1);
////
////        Client child = clientManager.createClient("Child", "Child", dateOfBirthChild,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        Client parent = clientManager.createClient("Parent", "Parent", dateOfBirthParent,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createJuniorAccount(child.getId(), parent.getId());
////        BankAccount account2 = accountManager.createSavingAccount(parent.getId(), BigDecimal.valueOf(0.1));
////        assertThrows(IllegalArgumentException.class, () -> transactionManager
////                .createJuniorOrSavingTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(100)));
//    }
//
//    @Test
//    void JuniorSavingAccountTransactionNegativeAmount() {
////        LocalDate dateOfBirthChild = LocalDate.of(2010, 1, 1);
////        LocalDate dateOfBirthParent = LocalDate.of(1990, 1, 1);
////
////        Client child = clientManager.createClient("Child", "Child", dateOfBirthChild,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        Client parent = clientManager.createClient("Parent", "Parent", dateOfBirthParent,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createJuniorAccount(child.getId(), parent.getId());
////        BankAccount account2 = accountManager.createSavingAccount(parent.getId(), BigDecimal.valueOf(0.1));
////        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
////        assertThrows(IllegalArgumentException.class, () -> transactionManager
////                .createJuniorOrSavingTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(-100)));
//    }
//
//    @Test
//    void JuniorNotExistingAccountTransaction() {
////        LocalDate dateOfBirthChild = LocalDate.of(2010, 1, 1);
////        LocalDate dateOfBirthParent = LocalDate.of(1990, 1, 1);
////
////        Client child = clientManager.createClient("Child", "Child", dateOfBirthChild,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        Client parent = clientManager.createClient("Parent", "Parent", dateOfBirthParent,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createJuniorAccount(child.getId(), parent.getId());
////        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
////        assertThrows(IllegalArgumentException.class, () -> transactionManager
////                .createJuniorOrSavingTransaction(account1.getAccountId(), 1000L, BigDecimal.valueOf(100)));
//    }
//
//    @Test
//    void JuniorStandardAccountTransaction() {
////        LocalDate dateOfBirthChild = LocalDate.of(2010, 1, 1);
////        LocalDate dateOfBirthParent = LocalDate.of(1990, 1, 1);
////
////        Client child = clientManager.createClient("Child", "Child", dateOfBirthChild,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        Client parent = clientManager.createClient("Parent", "Parent", dateOfBirthParent,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createJuniorAccount(child.getId(), parent.getId());
////        BankAccount account2 = accountManager.createStandardAccount(parent.getId(), BigDecimal.valueOf(1000));
////        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
////        transactionManager.createJuniorOrSavingTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(100));
////        assertEquals(BigDecimal.valueOf(0), account1.getBalance());
////        assertEquals(BigDecimal.valueOf(100), account2.getBalance());
//    }
//
//    @Test
//    void StandardJuniorAccountTransaction() {
////        LocalDate dateOfBirthChild = LocalDate.of(2010, 1, 1);
////        LocalDate dateOfBirthParent = LocalDate.of(1990, 1, 1);
////
////        Client child = clientManager.createClient("Child", "Child", dateOfBirthChild,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        Client parent = clientManager.createClient("Parent", "Parent", dateOfBirthParent,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createJuniorAccount(child.getId(), parent.getId());
////        BankAccount account2 = accountManager.createStandardAccount(parent.getId(), BigDecimal.valueOf(1000));
////        accountManager.depositMoney(account1.getAccountId(), BigDecimal.valueOf(100));
////        assertThrows(IllegalArgumentException.class, () -> transactionManager
////                .createJuniorOrSavingTransaction(account2.getAccountId(), account1.getAccountId(), BigDecimal.valueOf(100)));
//    }
//
//    @Test
//    void JuniorSavingAccountTransactionInActiveAccount() {
////        LocalDate dateOfBirthChild = LocalDate.of(2010, 1, 1);
////        LocalDate dateOfBirthParent = LocalDate.of(1990, 1, 1);
////
////        Client child = clientManager.createClient("Child", "Child", dateOfBirthChild,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        Client parent = clientManager.createClient("Parent", "Parent", dateOfBirthParent,
////                Client.ClientTypes.STANDARD, "street", "city", "1");
////        BankAccount account1 = accountManager.createJuniorAccount(child.getId(), parent.getId());
////        account1.setActive(false);
////        accountManager.update(account1);
////        BankAccount account2 = accountManager.createSavingAccount(parent.getId(), BigDecimal.valueOf(0.1));
////        assertThrows(IllegalArgumentException.class, () -> transactionManager
////                .createJuniorOrSavingTransaction(account1.getAccountId(), account2.getAccountId(), BigDecimal.valueOf(100)));
//    }
//
//}