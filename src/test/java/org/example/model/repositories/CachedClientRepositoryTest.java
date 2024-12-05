package org.example.model.repositories;

import org.example.model.RedisCache;
import org.example.model.clients.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CachedClientRepositoryTest {
    private static CachedClientRepository redisClientRepository;
    private static LocalDate dateOfBirth;

    private Client client;

    @BeforeAll
    static void beforeAll() {
        redisClientRepository = new CachedClientRepository(new ClientRepository(), new RedisCache());
        dateOfBirth = LocalDate.of(2000, 1, 1);
    }

    @BeforeEach
    void beforeEach() {
        client = new Client("John", "Doe", dateOfBirth, Client.ClientTypes.BUSINESS, "Street",
                "City", "42");
    }

    @Test
    void testAddGetClient() {
        redisClientRepository.add(client);
        Client client1 = redisClientRepository.findById(client.getId());
        assertEquals(client.getId(), client1.getId());
    }

    @Test
    void testDeleteClient() {
        redisClientRepository.add(client);
        redisClientRepository.delete(client.getId());
    }

    @Test
    void testUpdateClient() {
        redisClientRepository.add(client);

        Client client1 = redisClientRepository.findById(client.getId());
        client1.setClientType(Client.ClientTypes.ADVANCED);

        redisClientRepository.update(client1);

        Client client2 = redisClientRepository.findById(client.getId());
        assertEquals(client1.getClientType(), client2.getClientType());
    }
}
