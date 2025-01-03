package org.example.model.repositories;

import org.example.model.clients.Client;
import org.example.model.dto.ClientDTO;
import org.example.model.mappers.ClientDTOMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ClientRepository implements Repository<Client> {


    public ClientRepository() {
        super();
    }

    public Client add(Client client) {
        if (client == null) {
            return null;
//        }
//        InsertOneResult insertOneResult = clients.insertOne(ClientDTOMapper.toDTO(client));
//        if (!insertOneResult.wasAcknowledged()) {
//            return null;
//        }
        return client;
    }

    public List<Client> findAll() {
//        return clients.find().into(new ArrayList<>()).stream().map(ClientDTOMapper::fromDTO).toList();
    }

    public Client findById(UUID id) {
//        Bson filter = Filters.eq("_id", id);
//        ClientDTO client = clients.find(filter).first();
        if (client == null) {
            return null;
        }
//        return ClientDTOMapper.fromDTO(client);
    }

    public Client update(Client client) {
        if (client == null) {
            return null;
        }
//        Bson filter = Filters.eq("_id", client.getId());
//        // TODO: findAndReplace
//        Bson setUpdate = Updates.combine(
//                Updates.set("firstName", client.getFirstName()),
//                Updates.set("lastName", client.getLastName()),
//                Updates.set("dateOfBirth", client.getDateOfBirth()),
//                Updates.set("clientType", client.getClientType()),
//                Updates.set("city", client.getCity()),
//                Updates.set("street", client.getStreet()),
//                Updates.set("streetNumber", client.getStreetNumber()),
//                Updates.set("activeAccounts", client.getActiveAccounts())
//        );
//
//        UpdateResult updateResult = clients.updateOne(filter, setUpdate);
//        if (updateResult.getModifiedCount() == 0) {
//            return null;
//        }
        return client;
    }


    public Boolean delete(UUID id) {
//        Bson filter = Filters.eq("_id", id.toString());
//
//        DeleteResult deleteResult = clients.deleteOne(filter);
//        return deleteResult.getDeletedCount() != 0;
    }

    @Override
    public void close() {}

}
