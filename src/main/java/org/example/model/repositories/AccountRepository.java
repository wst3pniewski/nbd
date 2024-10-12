package org.example.model.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import org.example.model.accounts.BankAccount;
import org.example.model.accounts.BankAccount_;
import org.example.model.clients.Client;

import java.util.List;

import static jakarta.persistence.Persistence.createEntityManagerFactory;

public class AccountRepository implements AccountRepositoryInterface, AutoCloseable {

    private final EntityManagerFactory factory;

    public AccountRepository() {
        this.factory = createEntityManagerFactory("POSTGRES_RENT_PU");
    }

    @Override
    public BankAccount addAccount(BankAccount account) {
        try {
            Repository.inSession(factory, entityManager -> {
                entityManager.persist(account);
            });
        } catch (Exception e) {
            return null;
        }
        return account;
    }

    @Override
    public List<BankAccount> findAll() {
        final List<BankAccount>[] accounts = new List[1];
        Repository.inSession(factory, entityManager -> {
            var builder = factory.getCriteriaBuilder();
            CriteriaQuery<BankAccount> query = builder.createQuery(BankAccount.class);
            query.from(BankAccount.class);
            accounts[0] = entityManager.createQuery(query).getResultList();
        });
        return accounts[0];
    }

    @Override
    public BankAccount findById(Long id) {
        final BankAccount[] bankAccount = new BankAccount[1];
        Repository.inSession(factory, entityManager -> {
            var builder = factory.getCriteriaBuilder();
            CriteriaQuery<BankAccount> query = builder.createQuery(BankAccount.class);
            From<BankAccount, BankAccount> from = query.from(BankAccount.class);
            query.select(from).where(builder.equal(from.get(BankAccount_.id), id));
            bankAccount[0] = entityManager.createQuery(query).getSingleResult();
        });
        return bankAccount[0];
    }

    @Override
    public BankAccount updateAccount(BankAccount account) {
        try {
            Repository.inSession(factory, entityManager -> entityManager.merge(account));
        } catch (Exception e) {
            return null;
        }
        return account;
    }

    @Override
    public BankAccount deleteAccount(Long id) {
        final BankAccount[] bankAccount = new BankAccount[1];
        try {
            Repository.inSession(factory, entityManager -> {
                BankAccount account = entityManager.find(BankAccount.class, id);
                bankAccount[0] = account;
                entityManager.remove(account);
            });
        } catch (Exception e) {
            return null;
        }
        return bankAccount[0];
    }

    public int countClientActiveAccounts(Long clientId) {
        final int[] count = new int[1];
        Repository.inSession(factory, entityManager ->
        {
            Client client = entityManager.find(Client.class, clientId, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            var builder = factory.getCriteriaBuilder();
            CriteriaQuery<Long> query = builder.createQuery(Long.class);
            From<BankAccount, BankAccount> from = query.from(BankAccount.class);
            query.select(builder.count(from)).where(builder.and(
                    builder.equal(from.get(BankAccount_.client), client),
                    builder.equal(from.get(BankAccount_.isActive), true)
            ));
            count[0] = entityManager.createQuery(query).getSingleResult().intValue();
        });
        return count[0];
    }

    @Override
    public void close() throws Exception {
        this.factory.close();
    }
}
