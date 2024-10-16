package org.example.model.managers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;
import org.example.model.accounts.*;
import org.example.model.clients.Client;
import org.example.model.repositories.AccountRepository;
import org.example.model.repositories.ClientRepository;
import org.example.model.repositories.Repository;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.util.List;


/*
 * Business logic for managing account
 * - It is not possible to delete an account -> only set it to INACTIVE
 * - It is not possible to set an account to INACTIVE if it has any balance or debit
 * - It is not possible to create an account for a client who exceeded the limit of accounts
 * - It is not possible to create an account with a negative balance (constructor fulfilled)
 * - It is not possible to create an account with a negative debit   (constructor fulfilled)
 * - It is not possible to create a junior account for a client who is not a child (constructor fulfilled)
 * - It is not possible to create any other account for a client who is a child (constructor fulfilled)
 */
public class AccountManager {
    private AccountRepository accountRepository;
    private ClientRepository clientRepository;
    private EntityManagerFactory emf;

    public AccountManager(AccountRepository accountRepository, ClientRepository clientRepository, EntityManagerFactory emf) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        this.emf = emf;
    }

    public BankAccount createStandardAccount(long clientId, BigDecimal debitLimit) {
        BankAccount account = null;
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();  // Start a transaction manually
        try {
            tx.begin();
            Client client = clientRepository.findByIdWithOptimisticLock(clientId);
            long activeAccounts = accountRepository.countActiveByClientId(clientId);
            if (activeAccounts >= client.getClientType().getMaxActiveAccounts()) {
                throw new IllegalArgumentException("Client exceeded the limit of accounts");
            }
            account = new StandardAccount(client, debitLimit);
            accountRepository.add(account);
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

    public BankAccount createSavingAccount(long clientId, BigDecimal interestRate) {
        BankAccount account = null;
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();  // Start a transaction manually
        try {
            tx.begin();
            Client client = clientRepository.findByIdWithOptimisticLock(clientId);
            long activeAccounts = accountRepository.countActiveByClientId(clientId);
            if (activeAccounts >= client.getClientType().getMaxActiveAccounts()) {
                throw new IllegalArgumentException("Client exceeded the limit of accounts");
            }
            account = new SavingAccount(client, interestRate);
            accountRepository.add(account);
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

    public BankAccount createJuniorAccount(long clientId, long parentId) {
        BankAccount account = null;
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();  // Start a transaction manually
        try {
            tx.begin();
            Client client = clientRepository.findByIdWithOptimisticLock(clientId);
            int clientAge = client.getDateOfBirth().until(client.getDateOfBirth()).getYears();

            if (clientAge >= 18) {
                throw new IllegalArgumentException("Client is not a child");
            }

            Client parent = clientRepository.findById(parentId);
            long activeAccounts = accountRepository.countActiveByClientId(client.getId());
            if (activeAccounts >= client.getClientType().getMaxActiveAccounts()) {
                throw new IllegalArgumentException("Client exceeded the limit of accounts");
            }

            account = new JuniorAccount(client, parent);
            accountRepository.add(account);
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

    public BankAccount depositMoney(Long accountId, BigDecimal amount) {
        BankAccount account = null;
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();  // Start a transaction manually
        try {
            tx.begin();
            account = accountRepository.findByIdWithOptimisticLock(accountId);
            account.setBalance(amount);
            accountRepository.update(account);
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

    public BankAccount findById(Long id) {
        return accountRepository.findById(id);
    }

    public List<BankAccount> findAll() {
        return accountRepository.findAll();
    }

    public BankAccount update(BankAccount account) {
        return accountRepository.update(account);
    }

    public BankAccount delete(Long id) {
        BankAccount account = accountRepository.findById(id);
        return accountRepository.delete(id);
    }

    public BankAccount findByIdWithOptimisticLock(Long id) {
        return accountRepository.findByIdWithOptimisticLock(id);
    }

    public List<BankAccount> getAccountsByClientId(Long clientId) {
        return accountRepository.getAccountsByClientId(clientId);
    }

    public long countActiveByClientId(Long clientId) {
        return accountRepository.countActiveByClientId(clientId);
    }

}
