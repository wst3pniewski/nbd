package org.example.model.managers;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.example.model.accounts.BankAccount;
import org.example.model.accounts.StandardAccount;
import org.example.model.clients.Address;
import org.example.model.clients.Client;
import org.example.model.repositories.ClientRepository;
import org.hibernate.annotations.OptimisticLock;

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
    private EntityManager em;

    public ClientManager(EntityManager em) {
        this.clientRepository = new ClientRepository(em);
        this.em = em;
    }

    public Client createClient(String firstName, String lastName, LocalDate dateOfBirth, Client.ClientTypes clientType,
                               String street, String city, String zipCode) {
        Address address = new Address(street, city, zipCode);
        Client client = new Client(firstName, lastName, dateOfBirth, clientType, address);
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            clientRepository.add(client);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }

        return client;
    }

    public Client updateClient(Client client) {
        return clientRepository.update(client);
    }

    public Client deleteClient(Long id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Client client = clientRepository.findByIdWithOptimisticLock(id);
            if (client == null) {
                throw new IllegalArgumentException("Client not found");
            }
            clientRepository.delete(id);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("It is not possible to delete a client if he has any accounts." + e.getMessage());
        }

        return null;
    }

    public Client findById(Long id) {
        return clientRepository.findById(id);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

}
