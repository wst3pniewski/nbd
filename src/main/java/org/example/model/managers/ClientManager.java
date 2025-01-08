package org.example.model.managers;

import org.example.model.domain.clients.Client;
import org.example.model.repositories.AccountRepository;
import org.example.model.repositories.ClientRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class ClientManager {

    private ClientRepository clientRepository;
    private AccountRepository accountRepository;

    public ClientManager() {
        this.clientRepository = new ClientRepository();
        this.accountRepository = new AccountRepository();
    }

    public Client createClient(UUID clientId,
                               String clientType,
                               LocalDate dateOfBirth,
                               String firstName,
                               String lastName,
                               String street, String city, String zipCode) {
        Client client = new Client(clientId, clientType, dateOfBirth, firstName, lastName, street, city, zipCode);
        clientRepository.add(client);
        return client;
    }

    public void updateClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        if (clientRepository.findById(client.getClientId()) == null) {
            throw new IllegalArgumentException("Client not found");
        }
//        accountRepository.updateClient(client);
        clientRepository.update(client);
    }

    public Client deleteClient(UUID id) {
        Client client = clientRepository.findById(id);
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }
        if (accountRepository.countActiveByClientId(id) == 0) {
            clientRepository.delete(id);
            return client;
        }
        return null;
    }

    public Client findById(UUID id) {
        return clientRepository.findById(id);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

}
