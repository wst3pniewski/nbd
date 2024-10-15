package org.example.model.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import org.example.model.accounts.BankAccount;
import org.example.model.accounts.BankAccount_;


import java.util.List;


public class AccountRepository implements RepositoryI<BankAccount>, AutoCloseable {

    private EntityManager em;


    public AccountRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public BankAccount add(BankAccount account) {
        if (account == null) {
            return null;
        }
        em.persist(account);
        return account;
    }

    @Override
    public List<BankAccount> findAll() {
        List<BankAccount>[] accounts;
        var builder = em.getCriteriaBuilder();
        CriteriaQuery<BankAccount> query = builder.createQuery(BankAccount.class);
        query.from(BankAccount.class);
        return em.createQuery(query).getResultList();

    }

    @Override
    public BankAccount findById(Long id) {
        var builder = em.getCriteriaBuilder();
        CriteriaQuery<BankAccount> query = builder.createQuery(BankAccount.class);
        From<BankAccount, BankAccount> from = query.from(BankAccount.class);
        query.select(from).where(builder.equal(from.get(BankAccount_.id), id));
        return em.createQuery(query).getSingleResult();
    }

    @Override
    public BankAccount update(BankAccount account) {
        em.merge(account);
        return account;
    }

    @Override
    public BankAccount delete(Long id) {
        BankAccount account = em.find(BankAccount.class, id);
        em.remove(account);
        return account;
    }

    @Override
    public BankAccount findByIdWithOptimisticLock(Long id) {
        var builder = em.getCriteriaBuilder();
        CriteriaQuery<BankAccount> query = builder.createQuery(BankAccount.class);
        From<BankAccount, BankAccount> from = query.from(BankAccount.class);
        query.select(from).where(builder.equal(from.get(BankAccount_.id), id));
        return em.createQuery(query)
                .setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
                .getSingleResult();
    }

    public List<BankAccount> findByClientId(Long clientId) {
        var builder = em.getCriteriaBuilder();
        CriteriaQuery<BankAccount> query = builder.createQuery(BankAccount.class);
        From<BankAccount, BankAccount> from = query.from(BankAccount.class);
        query.select(from).where(builder.equal(from.get(BankAccount_.client).get("id"), clientId));
        return em.createQuery(query)
                .setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
                .getResultList();
    }

    @Override
    public void close() throws Exception {
        this.em.close();
    }
}
