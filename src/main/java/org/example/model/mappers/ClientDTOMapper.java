package org.example.model.mappers;

import org.example.model.clients.Client;
import org.example.model.dto.ClientDTO;

import java.util.UUID;

public class ClientDTOMapper {
    public static ClientDTO toDTO(Client client) {
        if (client == null) {
            return null;
        }
        return new ClientDTO(
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

    public static Client fromDTO(ClientDTO clientDTO) {
        if (clientDTO == null) {
            return null;
        }
        return new Client(
                UUID.fromString(clientDTO.getId().toString()),
                clientDTO.getFirstName(),
                clientDTO.getLastName(),
                clientDTO.getDateOfBirth(),
                clientDTO.getClientType(),
                clientDTO.getStreet(),
                clientDTO.getCity(),
                clientDTO.getStreetNumber(),
                clientDTO.getActiveAccounts()
        );
    }
}