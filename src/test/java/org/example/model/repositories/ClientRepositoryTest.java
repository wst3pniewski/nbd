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
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findByIdWithOptimisticLock() {
        EntityTransaction transaction = em.getTransaction();
        Address address = new Address("Ulica", "Lodz", "1");
        transaction.begin();
        clientRepository.add(new Client("John", "Doe",
                LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD, address));
        transaction.commit();
        transaction.begin();
        clientRepository.findByIdWithOptimisticLock(1L);
        transaction.commit();
        transaction.begin();
        clientRepository.findByIdWithOptimisticLock(1L);
        transaction.commit();
    }

    @Test
    void close() {
    }
}