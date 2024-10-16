package org.example.model.managers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.model.clients.Client;
import org.example.model.repositories.AccountRepository;
import org.example.model.repositories.ClientRepository;
import org.example.model.repositories.TransactionRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionManagerTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static TransactionManager transactionManager;


    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_RENT_PU");
        em = emf.createEntityManager();
        TransactionRepository transactionRepository = new TransactionRepository(em);
        transactionManager = new TransactionManager(transactionRepository);


    }

    @AfterAll
    static void afterAll() {
        if (emf != null) {
            emf.close();
        }
    }
    @Test
    void createStandardTransaction() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        ClientRepository clientRepository = new ClientRepository(em);
        ClientManager clientManager = new ClientManager(clientRepository);
        AccountRepository accountRepository = new AccountRepository(em);
        AccountManager accountManager = new AccountManager(accountRepository);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Client client1 = clientManager.createClient("Add", "Account", dateOfBirth,
                Client.ClientTypes.STANDARD, "Ulica", "Lodz", "1");
        accountManager.createStandardAccount(client1, BigDecimal.valueOf(1000));
        accountManager.createStandardAccount(client1, BigDecimal.valueOf(1000));
    }
}