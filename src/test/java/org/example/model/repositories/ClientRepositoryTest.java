package org.example.model.repositories;

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
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        //transaction
        clientRepository.add(client);
        //end
        Client foundClient = clientRepository.findById(client.getId());

        assertEquals(client.getId(), foundClient.getId());
    }

    @Test
    void findAll() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        //transaction
//        em.persist(client);
        //end
        List<Client> clients = clientRepository.findAll();

        assertTrue(clients.size() > 0);
    }

    @Test
    void findById() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        //transaction
//        em.persist(client);
        //end
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
        //transaction
//        em.persist(client);
        //end

        client.setClientType(Client.ClientTypes.STANDARD);
        //transaction
        clientRepository.update(client);
        //end

        Client foundClient = clientRepository.findById(client.getId());

        assertEquals(client.getClientType(), foundClient.getClientType());
    }

    @Test
    void delete() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        //transaction
//        em.persist(client);
        //end
        client.setClientType(Client.ClientTypes.STANDARD);
        //transaction
        clientRepository.delete(client.getId());
        //end
        Client foundClient = clientRepository.findById(client.getId());

        assertNull(foundClient);
    }

    @Test
    void deleteNotExistingClient() {
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        Address address = new Address("Aleja", "Lodz", "1");
        Client client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, address);
        //transaction
//        em.persist(client);
        //end
        client.setClientType(Client.ClientTypes.STANDARD);
        //transaction
        clientRepository.delete(client.getId());
        //end
        assertDoesNotThrow(() -> clientRepository.findById(1000L));
    }
}