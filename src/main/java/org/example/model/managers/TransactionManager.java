package org.example.model.managers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.model.Transaction;
import org.example.model.accounts.BankAccount;
import org.example.model.accounts.StandardAccount;
import org.example.model.repositories.AccountRepository;
import org.example.model.repositories.TransactionRepository;

import java.math.BigDecimal;

public class TransactionManager {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private EntityManager em;

    public TransactionManager(EntityManager em) {
        this.transactionRepository = new TransactionRepository(em);
        this.accountRepository = new AccountRepository(em);
        this.em = em;
    }

    public Transaction createStandardTransaction(long sourceAccountId, long destinationAccountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        Transaction transaction = null;
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            BankAccount sourceAccount = accountRepository.findByIdWithOptimisticLock(sourceAccountId);
            BankAccount destinationAccount = accountRepository.findByIdWithOptimisticLock(destinationAccountId);
            if (sourceAccount == null || destinationAccount == null) {
                throw new IllegalArgumentException("One of accounts not found");
            }
            if (!(sourceAccount instanceof StandardAccount)) {
                throw new IllegalArgumentException("Source account must be standard.");
            }
            if (!sourceAccount.getActive() || !destinationAccount.getActive()) {
                throw new IllegalArgumentException("One of accounts is not active");
            }
            BigDecimal debitLimit = ((StandardAccount) sourceAccount).getDebitLimit();
            BigDecimal debit = ((StandardAccount) sourceAccount).getDebit();
            BigDecimal availableDebit = debitLimit.subtract(debit);
            BigDecimal balance = sourceAccount.getBalance();
            BigDecimal balanceDebitSum = balance.add(availableDebit);
            if (balanceDebitSum.compareTo(amount) < 0) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            if (sourceAccount.getAccountId() == destinationAccount.getAccountId()) {
                throw new IllegalArgumentException("Source and destination accounts are the same");
            }
            sourceAccount.setBalance(sourceAccount.getBalance().subtract(balance));
            ((StandardAccount) sourceAccount).setDebit(debit.add(amount.subtract(balance)));
            destinationAccount.setBalance(destinationAccount.getBalance().add(amount));
            accountRepository.update(sourceAccount);
            accountRepository.update(destinationAccount);
            transaction = new Transaction(sourceAccount, destinationAccount, amount);
            transactionRepository.add(transaction);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }

        return transaction;
    }


    public Transaction createJuniorOrSavingTransaction(long sourceAccountId, long destinationAccountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        Transaction transaction = null;
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            BankAccount sourceAccount = accountRepository.findByIdWithOptimisticLock(sourceAccountId);
            BankAccount destinationAccount = accountRepository.findByIdWithOptimisticLock(destinationAccountId);
            if (sourceAccount == null || destinationAccount == null) {
                throw new IllegalArgumentException("One of accounts not found");
            }
            if ((sourceAccount instanceof StandardAccount)) {
                throw new IllegalArgumentException("Method only for junior and saving accounts.");
            }
            if (!sourceAccount.getActive() || !destinationAccount.getActive()) {
                throw new IllegalArgumentException("One of accounts is not active");
            }
            if (sourceAccount.getAccountId() == destinationAccount.getAccountId()) {
                throw new IllegalArgumentException("Source and destination accounts are the same");
            }

            BigDecimal balance = sourceAccount.getBalance();
            if (balance.compareTo(amount) < 0) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            sourceAccount.setBalance(sourceAccount.getBalance().subtract(balance));
            destinationAccount.setBalance(destinationAccount.getBalance().add(amount));
            accountRepository.update(sourceAccount);
            accountRepository.update(destinationAccount);
            transaction = new Transaction(sourceAccount, destinationAccount, amount);
            transactionRepository.add(transaction);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }

        return transaction;
    }
}
