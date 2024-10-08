package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import org.example.model.accounts.BankAccount;
import org.example.model.accounts.StandardAccount;
import org.example.model.clients.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class HibernateExampleTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;


    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_RENT_PU");
        em = emf.createEntityManager();
    }
    @AfterAll
    static void afterAll() {
        if (emf != null) {
            emf.close();
        }
    }
    @Test
    public void testConnection() {
        BankAccount account = em.find(StandardAccount.class, 1L);
        assertNull(account);
    }

    @Test
    public void insertAndSelectClient() {
        ClientType clientType = new BusinessClientType();
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", clientType, address);
        BankAccount account = new StandardAccount(client, BigDecimal.valueOf(1000));
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(clientType);
        em.persist(client);
        transaction.commit();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> query = cb.createQuery(Client.class);
        From<Client, Client> from = query.from(Client.class);
        query.select(from).where(cb.equal(from.get(Client_.id), "2"));
        Client found = em.createQuery(query).getSingleResult();
        assertEquals("John", found.getFirstName());
    }

    @Test
    public void countClients() {
        ClientType clientType = new BusinessClientType();
        Address address = new Address("Ulica", "Warszawa", "2");
        Client client = new Client("Alex", "Example", clientType, address);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(clientType);
        em.persist(client);
        transaction.commit();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        From<Client, Client> from = query.from(Client.class);
        query.select(cb.count(from));
        System.out.println(em.createQuery(query).getSingleResult());
    }
}
