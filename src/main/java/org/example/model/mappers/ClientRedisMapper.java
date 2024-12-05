package org.example.model.mappers;

import org.example.model.clients.Client;
import org.example.model.redis.ClientRedis;

import java.util.UUID;

public class ClientRedisMapper {

    public static ClientRedis toRedis(Client client) {
        if (client == null) {
            return null;
        }
        return new ClientRedis(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getDateOfBirth(),
                client.getClientType(),
                client.getStreet(),
                client.getCity(),
                client.getStreetNumber(),
                client.getActiveAccounts()
        );
    }

    public static Client fromRedis(ClientRedis clientRedis) {
        if (clientRedis == null) {
            return null;
        }
        return new Client(
                UUID.fromString(clientRedis.getId().toString()),
                clientRedis.getFirstName(),
                clientRedis.getLastName(),
                clientRedis.getDateOfBirth(),
                clientRedis.getClientType(),
                clientRedis.getStreet(),
                clientRedis.getCity(),
                clientRedis.getStreetNumber(),
                clientRedis.getActiveAccounts()
        );
    }
}
