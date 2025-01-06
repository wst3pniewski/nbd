//package org.example.model.managers;
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
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class AccountManagerTest {
//    private static AccountManager accountManager;
//    private static ClientManager clientManager;
//
//
//    @BeforeAll
//    static void beforeAll() {
//        clientManager = new ClientManager();
//        accountManager = new AccountManager();
//    }
//
//    @AfterAll
//    static void afterAll() {
//
//    }
//
//    @Test
//    void maxAccountsExceeded() {
//        Client client = clientManager.createClient("Max", "TwoAccounts",
//                LocalDate.of(2000, 1, 1),
//                Client.ClientTypes.STANDARD, "street", "city", "1");
//        accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        long activeAccounts = accountManager.countActiveByClientId(client.getId());
//
//        assertThrows(IllegalArgumentException.class, () -> accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000)));
//        assertEquals(2, activeAccounts);
//    }
//
//    @Test
//    void createStandardAccount() {
//        Client client = clientManager.createClient("John", "Doe",
//                LocalDate.of(2000, 1, 1),
//                Client.ClientTypes.STANDARD, "street", "city", "1");
//        BankAccount bankAccount = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        List<BankAccount> activeAccountsL = accountManager.getAccountsByClientId(client.getId());
//
//
//        assertEquals(1, activeAccountsL.size());
//        assertEquals(bankAccount.getId(), activeAccountsL.getFirst().getId());
//    }
//
//    @Test
//    void createSavingAccount() {
//        Client client = clientManager.createClient("Saving", "Account",
//                LocalDate.of(2000, 1, 1),
//                Client.ClientTypes.STANDARD, "street", "city", "1");
//        BankAccount bankAccount = accountManager.createSavingAccount(client.getId(), BigDecimal.valueOf(0.1));
//        List<BankAccount> activeAccountsL = accountManager.getAccountsByClientId(client.getId());
//
//        assertEquals(1, activeAccountsL.size());
//        assertEquals(bankAccount.getId(), activeAccountsL.getFirst().getId());
//    }
//
//    @Test
//    void createJuniorAccount() {
//        Client client = clientManager.createClient("Joe", "Junior",
//                LocalDate.of(2010, 1, 1),
//                Client.ClientTypes.STANDARD, "street", "city", "1");
//        Client parent = clientManager.createClient("John", "Doe",
//                LocalDate.of(1980, 1, 1),
//                Client.ClientTypes.STANDARD, "street", "city", "1");
//        BankAccount bankAccount = accountManager.createJuniorAccount(client.getId(), parent.getId());
//        List<BankAccount> activeAccountsL = accountManager.getAccountsByClientId(client.getId());
//
//
//        assertEquals(1, activeAccountsL.size());
//        assertEquals(bankAccount.getId(), activeAccountsL.getFirst().getId());
//    }
//
//    @Test
//    void createJuniorAccountAgeExceeded() {
//        Client client = clientManager.createClient("Joe", "Junior",
//                LocalDate.of(2000, 1, 1),
//                Client.ClientTypes.STANDARD, "street", "city", "1");
//        Client parent = clientManager.createClient("John", "Doe",
//                LocalDate.of(1980, 1, 1),
//                Client.ClientTypes.STANDARD, "street", "city", "1");
//        assertThrows(IllegalArgumentException.class, () -> accountManager.createJuniorAccount(client.getId(), parent.getId()));
//    }
//
//    @Test
//    void createStandardAccountAgeUnder18() {
//        Client client = clientManager.createClient("Joe", "Junior",
//                LocalDate.of(2020, 1, 1),
//                Client.ClientTypes.STANDARD, "street", "city", "1");
//        assertThrows(IllegalArgumentException.class, () -> accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000)));
//    }
//
//    @Test
//    void createSavingAccountAgeUnder18() {
//        Client client = clientManager.createClient("Joe", "Junior",
//                LocalDate.of(2020, 1, 1),
//                Client.ClientTypes.STANDARD, "street", "city", "1");
//        assertThrows(IllegalArgumentException.class, () -> accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(0.1)));
//    }
//
//    @Test
//    void payDebt() {
//        Client client = clientManager.createClient("Joe", "Junior",
//                LocalDate.of(2000, 1, 1),
//                Client.ClientTypes.STANDARD, "street", "city", "1");
//        BankAccount bankAccount = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        bankAccount.setBalance(BigDecimal.valueOf(1000));
//        ((StandardAccount) bankAccount).setDebit(BigDecimal.valueOf(500));
//        accountManager.update(bankAccount);
//        accountManager.payDebt(bankAccount.getId(), BigDecimal.valueOf(500));
//        BankAccount updatedAccount = accountManager.findById(bankAccount.getId());
//        assertEquals(BigDecimal.valueOf(0), ((StandardAccount) updatedAccount).getDebit());
//    }
//
//    @Test
//    void calculateAndAddInterest() {
//        Client client = clientManager.createClient("Saving", "Account",
//                LocalDate.of(2000, 1, 1),
//                Client.ClientTypes.STANDARD, "street", "city", "1");
//        BankAccount bankAccount = accountManager.createSavingAccount(client.getId(), BigDecimal.valueOf(0.1));
//        bankAccount.setBalance(BigDecimal.valueOf(1000));
//        accountManager.update(bankAccount);
//        accountManager.interestCalculation(bankAccount.getId());
//        assertEquals(BigDecimal.valueOf(1100.0), accountManager.findById(bankAccount.getId()).getBalance());
//    }
//
//    @Test
//    void depositMoney() {
//        Client client = clientManager.createClient("Joe", "Junior",
//                LocalDate.of(2000, 1, 1),
//                Client.ClientTypes.STANDARD, "street", "city", "1");
//        BankAccount bankAccount = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        accountManager.depositMoney(bankAccount.getId(), BigDecimal.valueOf(500));
//        BankAccount updatedAccount = accountManager.findById(bankAccount.getId());
//        assertEquals(BigDecimal.valueOf(500), updatedAccount.getBalance());
//    }
//
//    @Test
//    void withdrawMoney() {
//        Client client = clientManager.createClient("Joe", "Junior",
//                LocalDate.of(2000, 1, 1),
//                Client.ClientTypes.STANDARD, "street", "city", "1");
//        BankAccount bankAccount = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        accountManager.depositMoney(bankAccount.getId(), BigDecimal.valueOf(500));
//        accountManager.withdrawMoney(bankAccount.getId(), BigDecimal.valueOf(500));
//        BankAccount updatedAccount = accountManager.findById(bankAccount.getId());
//        assertEquals(BigDecimal.valueOf(0), updatedAccount.getBalance());
//    }
//
//    @Test
//    void withdrawMoneyOverBalance() {
//        Client client = clientManager.createClient("Joe", "Junior",
//                LocalDate.of(2000, 1, 1),
//                Client.ClientTypes.STANDARD, "street", "city", "1");
//        BankAccount bankAccount = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        accountManager.depositMoney(bankAccount.getId(), BigDecimal.valueOf(500));
//        assertThrows(IllegalArgumentException.class, () -> accountManager.withdrawMoney(bankAccount.getId(), BigDecimal.valueOf(1000)));
//    }
//
//    @Test
//    void clientUpdate(){
//        Client client = clientManager.createClient("Joe", "Junior",
//                LocalDate.of(2000, 1, 1),
//                Client.ClientTypes.STANDARD, "street", "city", "1");
//        BankAccount bankAccount = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//
//        assertEquals(bankAccount.getClient().getClientType(), Client.ClientTypes.STANDARD);
//
//        client.setClientType(Client.ClientTypes.BUSINESS);
//        clientManager.updateClient(client);
//
//        BankAccount updatedAccount = accountManager.findById(bankAccount.getId());
//        assertEquals(updatedAccount.getClient().getClientType(), Client.ClientTypes.BUSINESS);
//    }
//
//
//}