package org.example.model.managers;

import org.example.model.clients.Address;
import org.example.model.clients.Client;
import org.example.model.repositories.ClientRepository;

import java.time.LocalDate;
import java.util.List;
/*
* Business logic for managing clients
* - it is not possible to delete a client if he has any ACTIVE accounts
* - while creating a client no NULL values are allowed as for update
*
* */
public class ClientManager {
    private ClientRepository clientRepository;

    public ClientManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(String firstName, String lastName, LocalDate dateOfBirth, Client.ClientTypes clientType,
                               String street, String city, String zipCode) {
        Address address = new Address("street", "city", "zipCode");
        Client client = new Client(firstName, lastName, dateOfBirth, clientType, address);
        return clientRepository.addClient(client);
    }

    public Client updateClient(Client client) {
        return clientRepository.updateClient(client);
    }

    public Client deleteClient(Long id) {
        // TODO: check if client has any accounts
        return clientRepository.deleteClient(id);
    }

    public Client findById(Long id) {
        return clientRepository.findById(id);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

}
