package org.example.model.repositories;


import org.example.model.accounts.BankAccount;


import java.util.List;


public class AccountRepository implements Repository<BankAccount>, AccountRepositoryInterface, AutoCloseable {



    public AccountRepository() {
    }

    @Override
    public BankAccount add(BankAccount account) {
        if (account == null) {
            return null;
        }
//        em.persist(account);
        return account;
    }

    @Override
    public List<BankAccount> findAll() {
        List<BankAccount>[] accounts;
//        var builder = em.getCriteriaBuilder();
//        CriteriaQuery<BankAccount> query = builder.createQuery(BankAccount.class);
//        query.from(BankAccount.class);
//        return em.createQuery(query).getResultList();
        return null;
    }

    @Override
    public BankAccount findById(Long id) {
//        return em.find(BankAccount.class, id);
        return null;
    }

    @Override
    public BankAccount update(BankAccount account) {
        if (account == null) {
            return null;
        }
//        em.merge(account);
        return account;
    }

    @Override
    public BankAccount findByIdWithOptimisticLock(Long id) {
//        return em.find(BankAccount.class, id, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        return null;
    }

    @Override
    public List<BankAccount> getAccountsByClientId(Long clientId) {
//        var builder = em.getCriteriaBuilder();
//        CriteriaQuery<BankAccount> query = builder.createQuery(BankAccount.class);
//        From<BankAccount, BankAccount> from = query.from(BankAccount.class);
//        query.select(from).where(builder.equal(from.get(BankAccount_.client).get("id"), clientId));
//        return em.createQuery(query).getResultList();
        return null;
    }

    @Override
    public long countActiveByClientId(Long clientId) {
//        CriteriaBuilder builder = em.getCriteriaBuilder();
//        CriteriaQuery<Long> query = builder.createQuery(Long.class);
//        Root<BankAccount> root = query.from(BankAccount.class);
//        query.select(builder.count(root))
//                .where(builder.and(
//                        builder.equal(root.get("client").get("id"), clientId),
//                        builder.isTrue(root.get("isActive"))
//                ));
//        return em.createQuery(query).getSingleResult();
        return 0L;
    }

    @Override
    public void close() throws Exception {
//        this.em.close();
    }
}
