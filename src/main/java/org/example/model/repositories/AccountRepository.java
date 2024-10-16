package org.example.model.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;
import org.example.model.accounts.BankAccount;
import org.example.model.accounts.BankAccount_;
import org.example.model.clients.Client;


import java.util.List;


public class AccountRepository implements RepositoryI<BankAccount>, AutoCloseable {

    private EntityManagerFactory emf;


    public AccountRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public BankAccount add(BankAccount account) {
        if (account == null) {
            return null;
        }
        Repository.inSession(emf, em -> {
            em.persist(account);
        });
        return account;
    }

    @Override
    public List<BankAccount> findAll() {
        EntityManager em = emf.createEntityManager();
        List<BankAccount>[] accounts;
        var builder = em.getCriteriaBuilder();
        CriteriaQuery<BankAccount> query = builder.createQuery(BankAccount.class);
        query.from(BankAccount.class);
        return em.createQuery(query).getResultList();

    }

    @Override
    public BankAccount findById(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(BankAccount.class, id);
    }

    @Override
    public BankAccount update(BankAccount account) {
        if (account == null) {
            return null;
        }
        Repository.inSession(emf, em -> {
            em.merge(account);
        });
        return account;
    }

    @Override
    public BankAccount delete(Long id) {
        // TODO: implement delete
        EntityManager em = emf.createEntityManager();
        BankAccount account = em.find(BankAccount.class, id);
        em.remove(account);
        return account;
    }

    @Override
    public BankAccount findByIdWithOptimisticLock(Long id) {
        BankAccount account = null;
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            account = em.find(BankAccount.class, id, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
        return account;
    }

    public List<BankAccount> getAccountsByClientId(Long clientId) {
        EntityManager em = emf.createEntityManager();
        var builder = em.getCriteriaBuilder();
        CriteriaQuery<BankAccount> query = builder.createQuery(BankAccount.class);
        From<BankAccount, BankAccount> from = query.from(BankAccount.class);
        query.select(from).where(builder.equal(from.get(BankAccount_.client).get("id"), clientId));
        return em.createQuery(query).getResultList();
    }

    public long countActiveByClientId(Long clientId) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<BankAccount> root = query.from(BankAccount.class);
        query.select(builder.count(root))
                .where(builder.and(
                        builder.equal(root.get("client").get("id"), clientId),
                        builder.isTrue(root.get("isActive"))
                ));
        return em.createQuery(query).getSingleResult();
    }

    @Override
    public void close() throws Exception {
        this.emf.close();
    }
}
