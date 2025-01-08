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
    private static AccountManager accountManager;
    private Client client;

    @BeforeAll
    static void beforeAll() {
        clientManager = new ClientManager();
        accountManager = new AccountManager();
    }

    @AfterAll
    static void afterAll() {
    }

    @BeforeEach
    void setUp() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        client = clientManager.createClient(UUID.randomUUID(),
                Client.BUSINESS,
                dateOfBirth,
                "John",
                "Doe",
                "8th Avenue",
                "NYC",
                "1");
    }

    @Test
    void createClient() {
        assertNotNull(client);
        Client foundClient = clientManager.findById(client.getClientId());
        assertEquals(client.getClientId(), foundClient.getClientId());
    }

    @Test
    void updateClient() {
        client.setClientType(Client.BUSINESS);
        clientManager.updateClient(client);
        Client foundClient = clientManager.findById(client.getClientId());
        assertEquals(client.getClientType(), foundClient.getClientType());
    }

    @Test
    void deleteClient() {
        clientManager.deleteClient(client.getClientId());
    }

    // TODO: BUSINESS LOGIC
    @Test
    void deleteClientWithAccountsNotPossible() {
        accountManager.createStandardAccount(client.getClientId(), BigDecimal.valueOf(1000));
        clientManager.deleteClient(client.getClientId());
        Client foundClient = clientManager.findById(client.getClientId());
        assertNotNull(foundClient);
    }

    @Test
    void findById() {
        Client foundClient = clientManager.findById(client.getClientId());
        assertEquals(client.getClientId(), foundClient.getClientId());
    }

    @Test
    void findAll() {
        List<Client> clients = clientManager.findAll();
        assertFalse(clients.isEmpty());
    }

}