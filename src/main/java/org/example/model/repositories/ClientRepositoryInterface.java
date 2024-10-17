package org.example.model.repositories;

import org.example.model.clients.Client;

public interface ClientRepositoryInterface {
    public Client delete(Long id);
    public Client update(Client client);
}
