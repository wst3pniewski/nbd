//package org.example.model.managers;
//
//import org.example.model.domain.Transaction;
//import org.example.model.accounts.BankAccount;
//import org.example.model.accounts.StandardAccount;
//import org.example.model.repositories.AccountRepository;
//import org.example.model.repositories.TransactionRepository;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//public class TransactionManager {
//
//    private TransactionRepository transactionRepository;
//    private AccountRepository accountRepository;
//
//
//    public TransactionManager() {
//        this.transactionRepository = new TransactionRepository();
//        this.accountRepository = new AccountRepository();
//    }
//
//    public Transaction createStandardTransaction(UUID sourceAccountId, UUID destinationAccountId, BigDecimal amount) {
//        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
//            throw new IllegalArgumentException("Amount must be greater than zero");
//        }
//        Transaction transaction = null;
//
//        BankAccount sourceAccount = accountRepository.findById(sourceAccountId);
//        BankAccount destinationAccount = accountRepository.findById(destinationAccountId);
//        if (sourceAccount == null || destinationAccount == null) {
//            throw new IllegalArgumentException("One of accounts not found");
//        }
//        if (!(sourceAccount instanceof StandardAccount)) {
//            throw new IllegalArgumentException("Source account must be standard.");
//        }
//        if (!sourceAccount.getActive() || !destinationAccount.getActive()) {
//            throw new IllegalArgumentException("One of accounts is not active");
//        }
//        BigDecimal debitLimit = ((StandardAccount) sourceAccount).getDebitLimit();
//        BigDecimal debit = ((StandardAccount) sourceAccount).getDebit();
//        BigDecimal availableDebit = debitLimit.subtract(debit);
//        BigDecimal balance = sourceAccount.getBalance();
//        BigDecimal balanceDebitSum = balance.add(availableDebit);
//        if (balanceDebitSum.compareTo(amount) < 0) {
//            throw new IllegalArgumentException("Insufficient balance");
//        }
//        if (sourceAccount.getId() == destinationAccount.getId()) {
//            throw new IllegalArgumentException("Source and destination accounts are the same");
//        }
//        sourceAccount.setBalance(sourceAccount.getBalance().subtract(balance));
//        ((StandardAccount) sourceAccount).setDebit(debit.add(amount.subtract(balance)));
//        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));
//        accountRepository.update(sourceAccount);
//        accountRepository.update(destinationAccount);
//        transaction = new Transaction(sourceAccount.getId(), destinationAccount.getId(), amount);
//        transactionRepository.add(transaction);
//
//        return transaction;
//    }
//
//
//    public Transaction createJuniorOrSavingTransaction(UUID sourceAccountId, UUID destinationAccountId, BigDecimal amount) {
//        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
//            throw new IllegalArgumentException("Amount must be greater than zero");
//        }
//        Transaction transaction = null;
//
//        BankAccount sourceAccount = accountRepository.findById(sourceAccountId);
//        BankAccount destinationAccount = accountRepository.findById(destinationAccountId);
//        if (sourceAccount == null || destinationAccount == null) {
//            throw new IllegalArgumentException("One of accounts not found");
//        }
//        if ((sourceAccount instanceof StandardAccount)) {
//            throw new IllegalArgumentException("Method only for junior and saving accounts.");
//        }
//        if (!sourceAccount.getActive() || !destinationAccount.getActive()) {
//            throw new IllegalArgumentException("One of accounts is not active");
//        }
//        if (sourceAccount.getId() == destinationAccount.getId()) {
//            throw new IllegalArgumentException("Source and destination accounts are the same");
//        }
//
//        BigDecimal balance = sourceAccount.getBalance();
//        if (balance.compareTo(amount) < 0) {
//            throw new IllegalArgumentException("Insufficient balance");
//        }
//        sourceAccount.setBalance(sourceAccount.getBalance().subtract(balance));
//        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));
//        accountRepository.update(sourceAccount);
//        accountRepository.update(destinationAccount);
//        transaction = new Transaction(sourceAccount.getId(), destinationAccount.getId(), amount);
//        transactionRepository.add(transaction);
//
//        return transaction;
//    }
//}
