package org.example.model.repositories;

import org.example.model.Transaction;

import java.util.List;

public interface TransactionRepositoryInterface {
    Transaction addTransaction(Transaction transaction);

    List<Transaction> findAll();

    Transaction findById(Long id);

    Transaction updateTransaction(Transaction transaction);

    Transaction deleteTransaction(Long id);
}
