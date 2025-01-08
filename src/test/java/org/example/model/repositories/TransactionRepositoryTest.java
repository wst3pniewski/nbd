//package org.example.model.repositories;
//
//import org.example.model.domain.Transaction;
//import org.example.model.managers.AccountManager;
//import org.example.model.managers.ClientManager;
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
//class TransactionRepositoryTest {
//    private static TransactionRepository transactionRepository;
//    private static ClientManager clientManager;
//    private static AccountManager accountManager;
//
//    @BeforeAll
//    static void beforeAll() {
//        transactionRepository = new TransactionRepository();
//        clientManager = new ClientManager();
//        accountManager = new AccountManager();
//    }
//
//    @AfterAll
//    static void afterAll() {
//    }
//
//    @Test
//    void add() {
//        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
//
//        Client client = clientManager.createClient("Add", "Account", dateOfBirth,
//                Client.ClientTypes.STANDARD, "Ulica", "Lodz", "1");
//        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
//        Transaction transaction = new Transaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));
//
//        transactionRepository.add(transaction);
//
//        Transaction foundTransaction = transactionRepository.findById(transaction.getId());
//
//        assertEquals(transaction.getId(), foundTransaction.getId());
//    }
//
//    @Test
//    void findAll() {
//        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
//        Client client = clientManager.createClient("Add", "Account", dateOfBirth,
//                Client.ClientTypes.STANDARD, "Ulica", "Lodz", "1");
//
//        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
//
//        Transaction transaction = new Transaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));
//        Transaction transaction2 = new Transaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));
//
//        transactionRepository.add(transaction);
//        transactionRepository.add(transaction2);
//
//        List<Transaction> transactions = transactionRepository.findAll();
//        assertFalse(transactions.isEmpty());
//    }
//
//    @Test
//    void findById() {
//        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
//        Client client = clientManager.createClient("Add", "Account", dateOfBirth,
//                Client.ClientTypes.STANDARD, "Ulica", "Lodz", "1");
//
//        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        account1 = accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
//
//        Transaction transaction = new Transaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));
//
//        transactionRepository.add(transaction);
//
//        Transaction fountTransaction = transactionRepository.findById(transaction.getId());
//        assertEquals(transaction.getId(), fountTransaction.getId());
//    }
//
//    @Test
//    void deletePositive(){
//        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
//        Client client = clientManager.createClient("Add", "Account", dateOfBirth,
//                Client.ClientTypes.STANDARD, "Ulica", "Lodz", "1");
//
//        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        account1 = accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
//
//        Transaction transaction = new Transaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));
//
//        transactionRepository.add(transaction);
//
//        assertTrue(transactionRepository.delete(transaction.getId()));
//        assertNull(transactionRepository.findById(transaction.getId()));
//    }
//
//    @Test
//    void deleteNegative(){
//        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
//        Client client = clientManager.createClient("Add", "Account", dateOfBirth,
//                Client.ClientTypes.STANDARD, "Ulica", "Lodz", "1");
//
//        BankAccount account1 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        BankAccount account2 = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        account1 = accountManager.depositMoney(account1.getId(), BigDecimal.valueOf(100));
//
//        Transaction transaction = new Transaction(account1.getId(), account2.getId(), BigDecimal.valueOf(100));
//
//
//        assertFalse(transactionRepository.delete(transaction.getId()));
//        assertNull(transactionRepository.findById(transaction.getId()));
//    }
//
//}