//package org.example.model.managers;
//
//import org.example.model.clients.Client;
//import org.example.model.repositories.AccountRepository;
//import org.example.model.repositories.ClientRepository;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.UUID;
//
//
//public class ClientManager {
//
//    private ClientRepository clientRepository;
//    private AccountRepository accountRepository;
//
//    public ClientManager() {
//        this.clientRepository = new ClientRepository();
//        this.accountRepository = new AccountRepository();
//    }
//
//    public Client createClient(String firstName, String lastName, LocalDate dateOfBirth, Client.ClientTypes clientType,
//                               String street, String city, String zipCode) {
////        Address address = new Address(street, city, zipCode);
//        Client client = new Client(firstName, lastName, dateOfBirth, clientType, street, city, zipCode);
//        clientRepository.add(client);
//        return client;
//    }
//
//    public Client updateClient(Client client) {
//        accountRepository.updateClient(client);
//        return clientRepository.update(client);
//    }
//
//    public Client deleteClient(UUID id) {
//        Client client = clientRepository.findById(id);
//        if (client == null) {
//            throw new IllegalArgumentException("Client not found");
//        }
//        if (accountRepository.countActiveByClientId(id) == 0) {
//            clientRepository.delete(id);
//            return client;
//        }
//        return null;
//    }
//
//    public Client findById(UUID id) {
//        return clientRepository.findById(id);
//    }
//
//    public List<Client> findAll() {
//        return clientRepository.findAll();
//    }
//
//}
