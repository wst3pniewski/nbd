package org.example.model.repositories;

import org.example.model.domain.clients.Client;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientRepositoryTest {
    private static ClientRepository clientRepository;
    private Client client;

    @BeforeAll
    static void beforeAll() {
        clientRepository = new ClientRepository();
    }

    @AfterAll
    static void afterAll() {
        clientRepository.close();
    }

    @BeforeEach
    void setUp() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        client = new Client(UUID.randomUUID(),
                dateOfBirth,
                "John",
                "Doe",
                Client.BUSINESS,
                "8th Avenue",
                "NYC",
                "1");

        clientRepository.add(client);
    }

    @Test
    void addAndFindById() {
        Client foundClient = clientRepository.findById(client.getClientId());

        assertEquals(client.getClientId(), foundClient.getClientId());

        clientRepository.delete(client.getClientId());
    }

    @Test
    void findAll() {
        List<Client> clients = clientRepository.findAll();

        assertFalse(clients.isEmpty());

        clientRepository.delete(client.getClientId());
    }

    @Test
    void findNotExistingClient() {
        assertNull(clientRepository.findById(UUID.randomUUID()));
        assertDoesNotThrow(() -> clientRepository.findById(UUID.randomUUID()));
    }

    @Test
    void delete() {
        clientRepository.add(client);

        clientRepository.delete(client.getClientId());

        Client foundClient = clientRepository.findById(client.getClientId());

        assertNull(foundClient);
    }

    @Test
    void deleteNotExistingClient() {
        assertDoesNotThrow(() -> clientRepository.delete(UUID.randomUUID()));
    }

    @Test
    void update() {
        client.setClientType(Client.ADVANCED);

        clientRepository.update(client);

        Client foundClient = clientRepository.findById(client.getClientId());

        assertEquals(client.getClientType(), foundClient.getClientType());

        clientRepository.delete(client.getClientId());
    }
}