package org.example.model.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import org.example.model.clients.Client;

import java.util.List;


public class ClientRepository implements Repository<Client>, ClientRepositoryInterface, AutoCloseable {


    public ClientRepository() {

    }

    @Override
    public Client add(Client client) {
//        em.persist(client);
        return client;
    }

    @Override
    public List<Client> findAll() {
//        var builder = em.getCriteriaBuilder();
//        CriteriaQuery<Client> query = builder.createQuery(Client.class);
//        query.from(Client.class);
//        return em.createQuery(query).getResultList();
        return null;
    }

    @Override
    public Client findById(Long id) {
//        return em.find(Client.class, id);
        return null;
    }

    @Override
    public Client update(Client client) {
//        return em.merge(client);
        return null;
    }

    @Override
    public Client findByIdWithOptimisticLock(Long id) {
//        return em.find(Client.class, id, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        return null;
    }

    @Override
    public Client delete(Long id) {
//        Client foundClient = em.find(Client.class, id);
//        try {
//            em.remove(foundClient);
//        } catch (Exception e) {
//            System.out.println("Error deleting client: " + e.getMessage());
//        }
//        return foundClient;
        return null;
    }

    @Override
    public void close() throws Exception {
//        this.em.close();
    }

}
