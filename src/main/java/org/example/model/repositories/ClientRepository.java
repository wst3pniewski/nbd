package org.example.model.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.transaction.Transactional;
import org.example.model.clients.Client;
import org.example.model.clients.Client_;

import java.util.List;


public class ClientRepository implements RepositoryI<Client>, AutoCloseable {

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
//        var builder = em.getCriteriaBuilder();
//        CriteriaQuery<Client> query = builder.createQuery(Client.class);
//        From<Client, Client> from = query.from(Client.class);
//        query.select(from).where(builder.equal(from.get(Client_.id), id));
//
//        return em.createQuery(query).getSingleResult();
        return em.find(Client.class, id);
    }

    @Override
    public Client update(Client client) {
        em.merge(client);
        return client;
    }


    @Override
    @Transactional
    public Client delete(Long id) {
        Client foundClient = em.find(Client.class, id);
        em.remove(foundClient);
        return foundClient;
    }

    @Override
    public Client findByIdWithOptimisticLock(Long id) {
        Client client = em.find(Client.class, id, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        return client;
    }

    @Override
    public void close() throws Exception {
        this.em.close();
    }

}
