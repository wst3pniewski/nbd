package org.example.model.repositories;

import org.example.model.clients.Client;

import java.util.List;

public interface ClientRepositoryInterface {
    Client addClient(Client client);

    List<Client> findAll();

    Client findById(Long id);

    Client updateClient(Client client);

    Client deleteClient(Long id);
}
