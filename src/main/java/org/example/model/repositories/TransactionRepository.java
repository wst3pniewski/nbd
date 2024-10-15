package org.example.model.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import org.example.model.Transaction;
import org.example.model.Transaction_;

import java.util.List;


public class TransactionRepository implements RepositoryI<Transaction>, AutoCloseable {

    private EntityManager em;

    public TransactionRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Transaction add(Transaction transaction) {
        em.persist(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
        var builder = em.getCriteriaBuilder();
        CriteriaQuery<Transaction> query = builder.createQuery(Transaction.class);
        query.from(Transaction.class);
        return  em.createQuery(query).getResultList();
    }

    @Override
    public Transaction findById(Long id) {
        var builder = em.getCriteriaBuilder();
        CriteriaQuery<Transaction> query = builder.createQuery(Transaction.class);
        From<Transaction, Transaction> from = query.from(Transaction.class);
        query.select(from).where(builder.equal(from.get(Transaction_.id), id));
        return em.createQuery(query).getSingleResult();
    }

    @Override
    public Transaction update(Transaction transaction) {
        em.merge(transaction);
        return transaction;
    }

    @Override
    public Transaction delete(Long id) {
        Transaction foundTransaction = em.find(Transaction.class, id);
        em.remove(foundTransaction);
        return foundTransaction;
    }

    @Override
    public Transaction findByIdWithOptimisticLock(Long id) {
        var builder = em.getCriteriaBuilder();
        CriteriaQuery<Transaction> query = builder.createQuery(Transaction.class);
        From<Transaction, Transaction> from = query.from(Transaction.class);
        query.select(from).where(builder.equal(from.get(Transaction_.id), id));
        return em.createQuery(query)
                .setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
                .getSingleResult();
    }

    @Override
    public void close() throws Exception {
        this.em.close();
    }
}
