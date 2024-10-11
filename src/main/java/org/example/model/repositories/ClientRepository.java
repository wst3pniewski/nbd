package org.example.model.repositories;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import org.example.model.clients.Client;
import org.example.model.clients.Client_;

import static jakarta.persistence.Persistence.createEntityManagerFactory;

import java.util.List;

public class ClientRepository implements ClientRepositoryInterface, AutoCloseable {

    private final EntityManagerFactory factory;

    public ClientRepository() {
        this.factory = createEntityManagerFactory("POSTGRES_RENT_PU");
    }

    @Override
    public Client addClient(Client client) {
        try {
            Repository.inSession(factory, entityManager -> entityManager.persist(client));
        } catch (Exception e) {
            return null;
        }
        return client;
    }

    @Override
    public List<Client> findAll() {
        final List<Client>[] clients = new List[1];
        Repository.inSession(factory, entityManager -> {
            var builder = factory.getCriteriaBuilder();
            CriteriaQuery<Client> query = builder.createQuery(Client.class);
            query.from(Client.class);
            clients[0] = entityManager.createQuery(query).getResultList();
        });
        return clients[0];
    }

    @Override
    public Client findById(Long id) {
        final Client[] client = new Client[1];
        Repository.inSession(factory, entityManager -> {
            var builder = factory.getCriteriaBuilder();
            CriteriaQuery<Client> query = builder.createQuery(Client.class);
            From<Client, Client> from = query.from(Client.class);
            query.select(from).where(builder.equal(from.get(Client_.id), id));
            client[0] = entityManager.createQuery(query).getSingleResult();
        });
        return client[0];
    }

    @Override
    public Client updateClient(Client client) {
        try {
            Repository.inSession(factory, entityManager -> entityManager.merge(client));
        } catch (Exception e) {
            return null;
        }
        return client;
    }

    @Override
    public Client deleteClient(Long id) {
        final Client[] client = new Client[1];
        try {
            Repository.inSession(factory, entityManager -> {
                Client foundClient = entityManager.find(Client.class, id);
                client[0] = foundClient;
                entityManager.remove(foundClient);
            });
        } catch (Exception e) {
            return null;
        }
        return client[0];
    }

    @Override
    public void close() throws Exception {
        this.factory.close();
    }
}
