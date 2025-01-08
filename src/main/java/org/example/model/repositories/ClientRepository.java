package org.example.model.repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import org.example.model.domain.clients.Client;
import org.example.model.dao.ClientDao;
import org.example.model.mappers.ClientMapper;
import org.example.model.mappers.ClientMapperBuilder;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;


public class ClientRepository implements Repository<Client>, AutoCloseable {

    private static CqlSession session;
    private static ClientMapper clientMapper;
    private static ClientDao clientDao;

    public void initSession() {
        session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("cassandra1", 9042))
                .addContactPoint(new InetSocketAddress("cassandra2", 9043))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("cassandra", "cassandrapassword")
                .build();
        CreateKeyspace createKeyspace = createKeyspace(CqlIdentifier.fromCql("bank_accounts"))
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true);
        SimpleStatement statement = createKeyspace.build();
        session.execute(statement);
//        session.execute(SchemaBuilder.dropTable(CqlIdentifier.fromCql("bank_accounts"), CqlIdentifier.fromCql("clients")).ifExists().build());
        SimpleStatement createClients =
                SchemaBuilder.createTable(CqlIdentifier.fromCql("bank_accounts"), CqlIdentifier.fromCql("clients"))
                        .ifNotExists()
                        .withPartitionKey(CqlIdentifier.fromCql("client_id"), DataTypes.UUID)
                        .withColumn(CqlIdentifier.fromCql("first_name"), DataTypes.TEXT)
                        .withColumn(CqlIdentifier.fromCql("last_name"), DataTypes.TEXT)
                        .withColumn(CqlIdentifier.fromCql("date_of_birth"), DataTypes.DATE)
                        .withClusteringColumn(CqlIdentifier.fromCql("client_type"), DataTypes.TEXT)
                        .withColumn(CqlIdentifier.fromCql("street"), DataTypes.TEXT)
                        .withColumn(CqlIdentifier.fromCql("city"), DataTypes.TEXT)
                        .withColumn(CqlIdentifier.fromCql("street_number"), DataTypes.TEXT)
//                        .withColumn("active_accounts", SchemaBuilder.cint())
                        .build();
        session.execute(createClients);
    }

    public ClientRepository() {
        initSession();
        clientMapper = new ClientMapperBuilder(session).build();
        clientDao = clientMapper.clientDao();
    }


    public void add(Client client) {
        if (client == null) {
            return;
        }
        clientDao.create(client);
    }

    public List<Client> findAll() {
       return clientDao.findAll().all();
    }

    public Client findById(UUID id) {
        return clientDao.findById(id);
    }

    public void update(Client client) {
        if (client == null) {
            return;
        }
        Client oldClient = clientDao.findById(client.getClientId());
        clientDao.delete(oldClient);
        clientDao.create(client);
    }


    public void delete(UUID id) {
        Client client = clientDao.findById(id);
        if (client == null) {
            return;
        }
        clientDao.delete(client);
    }

    @Override
    public void close() {
        session.close();
    }

}
