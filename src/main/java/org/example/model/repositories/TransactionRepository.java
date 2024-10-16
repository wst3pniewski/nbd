package org.example.model.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import org.example.model.Transaction;
import org.example.model.Transaction_;

import java.util.List;


public class TransactionRepository implements RepositoryI<Transaction>, AutoCloseable {

    private EntityManagerFactory emf;

    public TransactionRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Transaction add(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        Repository.inSession(emf, em -> {
            em.persist(transaction);
        });
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
        EntityManager em = emf.createEntityManager();
        var builder = em.getCriteriaBuilder();
        CriteriaQuery<Transaction> query = builder.createQuery(Transaction.class);
        query.from(Transaction.class);
        return  em.createQuery(query).getResultList();
    }

    @Override
    public Transaction findById(Long id) {
        EntityManager em = emf.createEntityManager();
        var builder = em.getCriteriaBuilder();
        CriteriaQuery<Transaction> query = builder.createQuery(Transaction.class);
        From<Transaction, Transaction> from = query.from(Transaction.class);
        query.select(from).where(builder.equal(from.get(Transaction_.id), id));
        return em.createQuery(query).getSingleResult();
    }

    @Override
    public Transaction update(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        Repository.inSession(emf, em -> {
            em.merge(transaction);
        });
        return transaction;
    }

    @Override
    public Transaction delete(Long id) {
        Repository.inSession(emf, em -> {
            Transaction foundTransaction = em.find(Transaction.class, id, LockModeType.PESSIMISTIC_WRITE);
            em.remove(foundTransaction);
        });

        return null;
    }

    @Override
    public Transaction findByIdWithOptimisticLock(Long id) {
        EntityManager em = emf.createEntityManager();
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
        this.emf.close();
    }
}
