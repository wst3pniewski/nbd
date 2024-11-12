//package org.example.model.managers;
//
//import org.example.model.clients.Address;
//import org.example.model.clients.Client;
//import org.example.model.repositories.ClientRepository;
//
//
//import java.time.LocalDate;
//import java.util.List;
//
//
//public class ClientManager {
//    private ClientRepository clientRepository;
//
//    public ClientManager() {
////        this.clientRepository = new ClientRepository(em);
//    }
//
//    public Client createClient(String firstName, String lastName, LocalDate dateOfBirth, Client.ClientTypes clientType,
//                               String street, String city, String zipCode) {
//        Address address = new Address(street, city, zipCode);
//        Client client = new Client(firstName, lastName, dateOfBirth, clientType, address);
//        clientRepository.add(client);
//        return client;
//    }
//
//    public Client updateClient(Client client) {
//        return clientRepository.update(client);
//    }
//
//    public Client deleteClient(Long id) {
//
//        Client client = clientRepository.findByIdWithOptimisticLock(id);
//        if (client == null) {
//            throw new IllegalArgumentException("Client not found");
//        }
//        clientRepository.delete(id);
////        System.out.println("It is not possible to delete a client if he has any accounts." + e.getMessage());
//
//        return null;
//    }
//
//    public Client findById(Long id) {
//        return clientRepository.findById(id);
//    }
//
//    public List<Client> findAll() {
//        return clientRepository.findAll();
//    }
//
//}
