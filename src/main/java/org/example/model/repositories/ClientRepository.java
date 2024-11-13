package org.example.model.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.example.model.clients.Client;
import org.example.model.dto.ClientDTO;
import org.example.model.mappers.ClientDTOMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ClientRepository extends AbstractMongoRepository {

    private MongoCollection<ClientDTO> clients;

    public ClientRepository() {
        super();
        initDbConnection();
        this.clients = bankSystemDB.getCollection("clients", ClientDTO.class);
    }

    public Client add(Client client) {
        if (client == null) {
            return null;
        }
        clients.insertOne(ClientDTOMapper.toDTO(client));
        return client;
    }

    public List<Client> findAll() {
        return clients.find().into(new ArrayList<>()).stream().map(ClientDTOMapper::fromDTO).toList();
    }

    public Client findById(UUID id) {
        Bson filter = Filters.eq("_id", id);
        ClientDTO client = clients.find(filter).first();
        return ClientDTOMapper.fromDTO(client);
    }

    public Client update(Client client) {
        if (client == null) {
            return null;
        }
        Bson filter = Filters.eq("_id", client.getId());
        Bson setUpdate = Updates.combine(
                Updates.set("firstName", client.getFirstName()),
                Updates.set("lastName", client.getLastName()),
                Updates.set("dateOfBirth", client.getDateOfBirth()),
                Updates.set("clientType", client.getClientType()),
                Updates.set("city", client.getCity()),
                Updates.set("street", client.getStreet()),
                Updates.set("streetNumber", client.getStreetNumber())
//                Updates.set("address", client.getAddress())
        );

        clients.updateOne(filter, setUpdate);

        return client;
    }


    public Client delete(UUID id) {
        System.out.println(id.toString());
        Bson filter = Filters.eq("_id", id.toString());

        clients.deleteOne(filter);
        return null;
    }

    @Override
    public void close() throws Exception {
    }

}
