package org.example.model.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.transaction.Transactional;
import org.example.model.clients.Client;
import org.example.model.clients.Client_;

import java.util.List;


public class ClientRepository implements RepositoryI<Client>, AutoCloseable {

    private EntityManagerFactory emf;

    public ClientRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Client add(Client client) {
        Repository.inSession(emf, em -> em.persist(client));
        return client;
    }

    @Override
    public List<Client> findAll() {
        EntityManager em = emf.createEntityManager();
        var builder = em.getCriteriaBuilder();
        CriteriaQuery<Client> query = builder.createQuery(Client.class);
        query.from(Client.class);
        return em.createQuery(query).getResultList();

    }

    @Override
    public Client findById(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Client.class, id);
    }

    @Override
    public Client update(Client client) {
        Repository.inSession(emf, em -> em.merge(client));
        return client;
    }


    @Override
    public Client delete(Long id) {
        // TODO: implement delete
        EntityManager em = emf.createEntityManager();
        Client foundClient = em.find(Client.class, id);
        em.remove(foundClient);
        return foundClient;
    }

    @Override
    public Client findByIdWithOptimisticLock(Long id) {
        Client client = null;
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            client = em.find(Client.class, id, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
        return client;
    }

    @Override
    public void close() throws Exception {
        this.emf.close();
    }

}
