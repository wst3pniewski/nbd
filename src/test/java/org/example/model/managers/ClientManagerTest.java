package org.example.model.managers;

import jakarta.persistence.*;
import org.example.model.accounts.BankAccount;
import org.example.model.accounts.StandardAccount;
import org.example.model.clients.Address;
import org.example.model.clients.Client;
import org.example.model.repositories.AccountRepository;
import org.example.model.repositories.ClientRepository;
import org.example.model.repositories.Repository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientManagerTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static ClientManager clientManager;
    private static ClientRepository clientRepository;


    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_RENT_PU");
        em = emf.createEntityManager();
        ClientRepository clientRepository_ = new ClientRepository(emf);
        clientRepository = clientRepository_;
        clientManager = new ClientManager(clientRepository_);
    }

    @AfterAll
    static void afterAll() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void createClient() {
        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
                "Street", "City", "12345");
        assertNotNull(client);
//        Client foundClient = clientManager.findById(client.getId());
//        assertEquals(client.getId(), foundClient.getId());
    }

    @Test
    void updateClient() {
    }

    @Test
    void deleteClient() {
        EntityTransaction transaction = em.getTransaction();
        AccountRepository accountRepository = new AccountRepository(emf);
        AccountManager accountManager = new AccountManager(accountRepository, new ClientRepository(emf), emf);
        transaction.begin();
        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
                "Street", "City", "12345");
        BankAccount account = accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        transaction.commit();

        transaction.begin();
        Client foundC = clientManager.findById(1L);
        clientManager.deleteClient(1L);
        transaction.commit();
    }

    @Test
    void findById() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
                "Street", "City", "12345");
        Client foundC = clientManager.findById(client.getId());
        transaction.commit();
        assertEquals(client.getId(), foundC.getId());
    }

    @Test
    void findAll() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
                "Street", "City", "12345");
        transaction.commit();

        transaction.begin();
        List<Client> clients = clientManager.findAll();
        transaction.commit();
        assertTrue(clients.isEmpty() == false);
    }

    @Test
    void findByIdOptimisticLockException() {
//        EntityTransaction transaction = em.getTransaction();
//        transaction.begin();
//        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
//                "Street", "City", "12345");
//        transaction.commit();
//        EntityManager em2 = emf.createEntityManager();
//        ClientRepository clientRepository2 = new ClientRepository(em2);
//        ClientManager clientManager2 = new ClientManager(clientRepository2);
//        EntityTransaction transaction2 = em2.getTransaction();
//        transaction.begin();
//        transaction2.begin();
//        Client foundC = clientRepository.findByIdWithOptimisticLock(client.getId());
//        assertThrows(OptimisticLockException.class, () -> clientManager2.findById(client.getId()));
//        transaction.commit();
//        transaction2.commit();
    }
}