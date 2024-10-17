package org.example.model.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.model.clients.Address;
import org.example.model.clients.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientRepositoryTest {
    private static ClientRepository clientRepository;
    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_RENT_PU");
        em = emf.createEntityManager();
        clientRepository = new ClientRepository(em);
    }

    @AfterAll
    static void afterAll() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void add() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        clientRepository.add(client);
        transaction.commit();
        Client foundClient = clientRepository.findById(client.getId());

        assertEquals(client.getId(), foundClient.getId());
    }

    @Test
    void findAll() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(client);
        transaction.commit();
        List<Client> clients = clientRepository.findAll();

        assertTrue(clients.size() > 0);
    }

    @Test
    void findById() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(client);
        transaction.commit();
        Client foundClient = clientRepository.findById(client.getId());
        assertEquals(client.getId(), foundClient.getId());
    }

    @Test
    void findNotExistingClient() {
        assertNull(clientRepository.findById(1000L));
        assertDoesNotThrow(() -> clientRepository.findById(1000L));
    }

    @Test
    void update() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(client);
        transaction.commit();
        client.setClientType(Client.ClientTypes.STANDARD);
        transaction.begin();
        clientRepository.update(client);
        transaction.commit();

        Client foundClient = clientRepository.findById(client.getId());

        assertEquals(client.getClientType(), foundClient.getClientType());
    }

    @Test
    void delete() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(client);
        transaction.commit();
        client.setClientType(Client.ClientTypes.STANDARD);
        transaction.begin();
        clientRepository.delete(client.getId());
        transaction.commit();
        Client foundClient = clientRepository.findById(client.getId());

        assertNull(foundClient);
    }

    @Test
    void deleteNotExistingClient() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(client);
        transaction.commit();
        client.setClientType(Client.ClientTypes.STANDARD);
        transaction.begin();
        clientRepository.delete(client.getId());
        transaction.commit();
        assertDoesNotThrow(() -> clientRepository.findById(1000L));
    }
}