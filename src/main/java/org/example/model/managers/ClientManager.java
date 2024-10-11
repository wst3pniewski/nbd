package org.example.model.managers;

import org.example.model.clients.Address;
import org.example.model.clients.Client;
import org.example.model.clients.ClientType;
import org.example.model.repositories.ClientRepository;

import java.util.List;

public class ClientManager {
    private ClientRepository clientRepository;

    public ClientManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(String firstName, String lastName, int age, Client.ClientTypes clientType,
                               String street, String city, String zipCode) {
        Address address = new Address("street", "city", "zipCode");
        Client client = new Client(firstName, lastName, age, clientType, address);
        return clientRepository.addClient(client);
    }

    public Client updateClient(Client client) {
        return clientRepository.updateClient(client);
    }

    public Client deleteClient(Long id) {
        // It is not possible to delete a client if he has any accounts
        return clientRepository.deleteClient(id);
    }

    public Client findById(Long id) {
        return clientRepository.findById(id);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

}
