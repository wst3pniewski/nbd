package org.example.model.managers;

import org.example.model.Transaction;
import org.example.model.accounts.BankAccount;
import org.example.model.repositories.TransactionRepository;

import java.math.BigDecimal;

public class TransactionManager {

    private TransactionRepository transactionRepository;

    public TransactionManager(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createStandardTransaction(BankAccount sourceAccount, BankAccount destinationAccount, BigDecimal amount) {
        Transaction transaction = new Transaction(sourceAccount, destinationAccount, amount);
        return transactionRepository.add(transaction);

    }
}
