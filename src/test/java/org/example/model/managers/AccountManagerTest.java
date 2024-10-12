package org.example.model.managers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.model.clients.Address;
import org.example.model.clients.Client;
import org.example.model.repositories.ClientRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccountManagerTest {
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
    void createStandardAccount() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Ulica", "Lodz", "1");
        Client client = new Client("Add", "Account", dateOfBirth, Client.ClientTypes.STANDARD, address);
        AccountManager accountManager = new AccountManager();
        ClientRepository clientRepository = new ClientRepository();
        ClientManager clientManager = new ClientManager(clientRepository);
        Client client1 = clientManager.createClient("Add", "Account", dateOfBirth, Client.ClientTypes.STANDARD, "Ulica", "Lodz", "1");
        assertNotNull(client1);
        accountManager.createStandardAccount(client1, BigDecimal.valueOf(1000));
        accountManager.createStandardAccount(client1, BigDecimal.valueOf(1000));
        assertThrows(IllegalStateException.class, () -> accountManager.createStandardAccount(client1, BigDecimal.valueOf(1000)));
    }

    @Test
    void createSavingAccount() {
    }

    @Test
    void createJuniorAccount() {
    }
}