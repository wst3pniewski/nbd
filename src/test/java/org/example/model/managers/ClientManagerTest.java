package org.example.model.managers;

import org.example.model.domain.clients.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientManagerTest {
    private static ClientManager clientManager;
    private org.example.model.domain.clients.Client client;

    @BeforeAll
    static void beforeAll() {
        clientManager = new ClientManager();
    }

    @AfterAll
    static void afterAll() {
    }

    @BeforeEach
    void setUp() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        client = clientManager.createClient(UUID.randomUUID(),
                "BUSSINESS",
                dateOfBirth,
                "John",
                "Doe",
                "8th Avenue",
                "NYC",
                "1");
    }

    @Test
    void createClient() {
//        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
//                "Street", "City", "12345");
        assertNotNull(client);
        Client foundClient = clientManager.findById(client.getClientId());
        assertEquals(client.getClientId(), foundClient.getClientId());
    }

    @Test
    void updateClient() {
//        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
//                "Street", "City", "12345");
        client.setClientType(Client.BUSINESS);
        clientManager.updateClient(client);
        Client foundClient = clientManager.findById(client.getClientId());

        assertEquals(client.getClientType(), foundClient.getClientType());
    }

    @Test
    void deleteClient() {
//        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
//                "Street", "City", "12345");
        clientManager.deleteClient(client.getClientId());
    }

//    // TODO: BUSINESS LOGIC
//    @Test
//    void deleteClientWithAccountsNotPossible() {
//        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
//                "Street", "City", "12345");
//        AccountManager accountManager = new AccountManager();
//        accountManager.createStandardAccount(client.getId(), BigDecimal.valueOf(1000));
//        clientManager.deleteClient(client.getId());
//        Client foundClient = clientManager.findById(client.getId());
//        assertNotNull(foundClient);
//    }

    @Test
    void findById() {
//        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
//                "Street", "City", "12345");
        Client foundClient = clientManager.findById(client.getClientId());
        assertEquals(client.getClientId(), foundClient.getClientId());
    }

    @Test
    void findAll() {
//        Client client = clientManager.createClient("John", "Doe", LocalDate.of(1990, 1, 1), Client.ClientTypes.STANDARD,
//                "Street", "City", "12345");

        List<Client> clients = clientManager.findAll();
        assertTrue(clients.isEmpty() == false);
    }

}