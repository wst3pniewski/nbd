package org.example.model.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import org.example.model.clients.Client;

import java.util.List;


public class ClientRepository implements Repository<Client>, ClientRepositoryInterface, AutoCloseable {

    private EntityManager em;

    public ClientRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Client add(Client client) {
        em.persist(client);
        return client;
    }

    @Override
    public List<Client> findAll() {
        var builder = em.getCriteriaBuilder();
        CriteriaQuery<Client> query = builder.createQuery(Client.class);
        query.from(Client.class);
        return em.createQuery(query).getResultList();

    }

    @Override
    public Client findById(Long id) {
        return em.find(Client.class, id);
    }

    @Override
    public Client update(Client client) {
        return em.merge(client);
    }

    @Override
    public Client findByIdWithOptimisticLock(Long id) {
        return em.find(Client.class, id, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    }

    @Override
    public Client delete(Long id) {
        Client foundClient = em.find(Client.class, id);
        try {
            em.remove(foundClient);
        } catch (Exception e) {
            System.out.println("Error deleting client: " + e.getMessage());
        }
        return foundClient;
    }

    @Override
    public void close() throws Exception {
        this.em.close();
    }

}
