package org.example.model.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import org.example.model.Transaction;
import org.example.model.Transaction_;

import java.util.List;


public class TransactionRepository implements Repository<Transaction>, AutoCloseable {


    public TransactionRepository() {

    }

    @Override
    public Transaction add(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
//        em.persist(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
//        var builder = em.getCriteriaBuilder();
//        CriteriaQuery<Transaction> query = builder.createQuery(Transaction.class);
//        query.from(Transaction.class);
//        return em.createQuery(query).getResultList();
        return null;
    }

    @Override
    public Transaction findById(Long id) {
//        var builder = em.getCriteriaBuilder();
//        CriteriaQuery<Transaction> query = builder.createQuery(Transaction.class);
//        From<Transaction, Transaction> from = query.from(Transaction.class);
//        query.select(from).where(builder.equal(from.get(Transaction_.id), id));
//        return em.createQuery(query).getSingleResult();
        return null;
    }

    @Override
    public Transaction findByIdWithOptimisticLock(Long id) {
//        var builder = em.getCriteriaBuilder();
//        CriteriaQuery<Transaction> query = builder.createQuery(Transaction.class);
//        From<Transaction, Transaction> from = query.from(Transaction.class);
//        query.select(from).where(builder.equal(from.get(Transaction_.id), id));
//        return em.createQuery(query)
//                .setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
//                .getSingleResult();
        return null;
    }

    @Override
    public void close() throws Exception {
//        this.em.close();
    }
}
