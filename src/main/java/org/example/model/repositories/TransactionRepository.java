package org.example.model.repositories;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import org.example.model.Transaction;
import org.example.model.Transaction_;

import java.util.List;

import static jakarta.persistence.Persistence.createEntityManagerFactory;

public class TransactionRepository implements TransactionRepositoryInterface, AutoCloseable {

    private final EntityManagerFactory factory;

    public TransactionRepository() {
        this.factory = createEntityManagerFactory("POSTGRES_RENT_PU");
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        try {
            Repository.inSession(factory, entityManager -> entityManager.persist(transaction));
        } catch (Exception e) {
            return null;
        }
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
        final List<Transaction>[] transactions = new List[1];
        Repository.inSession(factory, entityManager -> {
            var builder = factory.getCriteriaBuilder();
            CriteriaQuery<Transaction> query = builder.createQuery(Transaction.class);
            query.from(Transaction.class);
            transactions[0] = entityManager.createQuery(query).getResultList();
        });
        return transactions[0];
    }

    @Override
    public Transaction findById(Long id) {
        final Transaction[] transaction = new Transaction[1];
        Repository.inSession(factory, entityManager -> {
            var builder = factory.getCriteriaBuilder();
            CriteriaQuery<Transaction> query = builder.createQuery(Transaction.class);
            From<Transaction, Transaction> from = query.from(Transaction.class);
            query.select(from).where(builder.equal(from.get(Transaction_.id), id));
            transaction[0] = entityManager.createQuery(query).getSingleResult();
        });
        return transaction[0];
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) {
        try {
            Repository.inSession(factory, entityManager -> entityManager.merge(transaction));
        } catch (Exception e) {
            return null;
        }
        return transaction;
    }

    @Override
    public Transaction deleteTransaction(Long id) {
        final Transaction[] transaction = new Transaction[1];
        try {
            Repository.inSession(factory, entityManager -> {
                Transaction foundTransaction = entityManager.find(Transaction.class, id);
                transaction[0] = foundTransaction;
                entityManager.remove(foundTransaction);
            });
        } catch (Exception e) {
            return null;
        }
        return transaction[0];
    }

    @Override
    public void close() throws Exception {
        this.factory.close();
    }
}
