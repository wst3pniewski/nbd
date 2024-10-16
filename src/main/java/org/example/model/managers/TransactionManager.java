package org.example.model.managers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.example.model.Transaction;
import org.example.model.accounts.BankAccount;
import org.example.model.accounts.StandardAccount;
import org.example.model.clients.Client;
import org.example.model.repositories.AccountRepository;
import org.example.model.repositories.TransactionRepository;

import java.math.BigDecimal;

public class TransactionManager {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private EntityManagerFactory emf;

    public TransactionManager(TransactionRepository transactionRepository,AccountRepository accountRepository, EntityManagerFactory emf) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.emf = emf;
    }

    public Transaction createStandardTransaction(long sourceAccountId, long destinationAccountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        Transaction transaction = null;
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();  // Start a transaction manually
        try {
            tx.begin();
            BankAccount sourceAccount = accountRepository.findByIdWithOptimisticLock(sourceAccountId);
            BankAccount destinationAccount = accountRepository.findByIdWithOptimisticLock(destinationAccountId);
            BigDecimal debitLimit = ((StandardAccount)sourceAccount).getDebitLimit();
            BigDecimal debit = ((StandardAccount)sourceAccount).getDebit();
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
        } finally {
            em.close();
        }

        return transaction;
    }
}
