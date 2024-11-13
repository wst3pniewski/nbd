package org.example.model.mappers;

import org.example.model.clients.Client;
import org.example.model.dto.ClientDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientDTOMapperTest {

    @Test
    void MapClientToClientDTO() {

        UUID id = UUID.randomUUID();
        String firstName = "John";
        String lastName = "Doe";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        Client.ClientTypes clientType = Client.ClientTypes.STANDARD;
        String street = "123 Main St";
        String city = "Anytown";
        String number = "123";

        Client client = new Client(id, firstName, lastName, dateOfBirth, clientType, street, city, number);


        ClientDTO clientDTO = ClientDTOMapper.toDTO(client);


        assertEquals(id, clientDTO.getId());
        assertEquals(firstName, clientDTO.getFirstName());
        assertEquals(lastName, clientDTO.getLastName());
        assertEquals(dateOfBirth, clientDTO.getDateOfBirth());
        assertEquals(clientType, clientDTO.getClientType());
        assertEquals(street, clientDTO.getStreet());
        assertEquals(city, clientDTO.getCity());
        assertEquals(number, clientDTO.getStreetNumber());
    }

    @Test
    void MapClientDTOToClient() {

        UUID id = UUID.randomUUID();
        String firstName = "John";
        String lastName = "Doe";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        Client.ClientTypes clientType = Client.ClientTypes.STANDARD;
        String street = "123 Main St";
        String city = "Anytown";
        String number = "123";

        ClientDTO clientDTO = new ClientDTO(id, firstName, lastName, dateOfBirth, clientType, street, city, number);

        Client client = ClientDTOMapper.fromDTO(clientDTO);

        assertEquals(id, client.getId());
        assertEquals(firstName, client.getFirstName());
        assertEquals(lastName, client.getLastName());
        assertEquals(dateOfBirth, client.getDateOfBirth());
        assertEquals(clientType, client.getClientType());
        assertEquals(street, client.getStreet());
        assertEquals(city, client.getCity());
        assertEquals(number, client.getStreetNumber());
    }
}