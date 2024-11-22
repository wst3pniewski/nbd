package org.example.model.repositories;

import org.example.model.clients.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientRepositoryTest {
    private static ClientRepository clientRepository;

    @BeforeAll
    static void beforeAll() {
        clientRepository = new ClientRepository();
    }

    @AfterAll
    static void afterAll() {

    }

    @Test
    void add() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, "Aleja",
                "Lodz", "1");

        clientRepository.add(client);

        Client foundClient = clientRepository.findById(client.getId());

        assertEquals(client.getId(), foundClient.getId());

//        clientRepository.delete(client.getId());
    }

    @Test
    void findAll() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Client client = new Client( "John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, "Aleja",
                "Lodz", "1");

        clientRepository.add(client);

        List<Client> clients = clientRepository.findAll();

        assertFalse(clients.isEmpty());

        clientRepository.delete(client.getId());
    }

    @Test
    void findById() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, "Aleja",
                "Lodz", "1");

        clientRepository.add(client);

        Client foundClient = clientRepository.findById(client.getId());
        assertEquals(client.getId(), foundClient.getId());

        clientRepository.delete(client.getId());
    }

    @Test
    void findNotExistingClient() {
        assertNull(clientRepository.findById(UUID.randomUUID()));
        assertDoesNotThrow(() -> clientRepository.findById(UUID.randomUUID()));
    }

    @Test
    void update() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS,
                "Aleja", "Lodz", "1");

        clientRepository.add(client);

        client.setClientType(Client.ClientTypes.STANDARD);

        clientRepository.update(client);

        Client foundClient = clientRepository.findById(client.getId());

        assertEquals(client.getClientType(), foundClient.getClientType());

        clientRepository.delete(client.getId());
    }

    @Test
    void delete() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, "Aleja", "Lodz", "1");

        clientRepository.add(client);

        client.setClientType(Client.ClientTypes.STANDARD);

        clientRepository.delete(client.getId());

        Client foundClient = clientRepository.findById(client.getId());

        assertNull(foundClient);
    }

    @Test
    void deleteNotExistingClient() {
        assertDoesNotThrow(() -> clientRepository.delete(UUID.randomUUID()));
    }
}