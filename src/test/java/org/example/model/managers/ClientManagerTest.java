package org.example.model.managers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
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


    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_RENT_PU");
        em = emf.createEntityManager();
        ClientRepository clientRepository = new ClientRepository(em);
        clientManager = new ClientManager(clientRepository);


    }

    @AfterAll
    static void afterAll() {
        if (emf != null) {
            emf.close();
        }
    }
    @Test
    void createClient() {

    }

    @Test
    void updateClient() {
    }

    @Test
    void deleteClient() {
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
                "Street", "City", "12345");
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

}