package org.example.model.managers;

import org.example.model.clients.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientManagerTest {
    private static ClientManager clientManager;


    @BeforeAll
    static void beforeAll() {
        clientManager = new ClientManager();
    }

    @AfterAll
    static void afterAll() {
    }

    @Test
    void createClient() {
        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
                "Street", "City", "12345");
        assertNotNull(client);
        Client foundClient = clientManager.findById(client.getId());
        assertEquals(client.getId(), foundClient.getId());
    }

    @Test
    void updateClient() {
        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
                "Street", "City", "12345");
        client.setClientType(Client.ClientTypes.BUSINESS);
        clientManager.updateClient(client);
        Client foundClient = clientManager.findById(client.getId());

        assertEquals(client.getClientType(), foundClient.getClientType());
    }

    @Test
    void deleteClient() {
        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
                "Street", "City", "12345");
        clientManager.deleteClient(client.getId());
    }

    // TODO: BUSINESS LOGIC
    @Test
    void deleteClientWithAccountsNotPossible() {
        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
                "Street", "City", "12345");
        AccountManager accountManager = new AccountManager();
        accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
        clientManager.deleteClient(client.getId());
        Client foundClient = clientManager.findById(client.getId());
        assertNotNull(foundClient);
    }

    @Test
    void findById() {
        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
                "Street", "City", "12345");
        Client foundC = clientManager.findById(client.getId());
        assertEquals(client.getId(), foundC.getId());
    }

    @Test
    void findAll() {
        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
                "Street", "City", "12345");

        List<Client> clients = clientManager.findAll();
        assertTrue(clients.isEmpty() == false);
    }

}