package org.example.model.repositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationAction;
import com.mongodb.client.model.ValidationOptions;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.example.model.codecs.UUIDCodec;
import org.example.model.dto.*;

import java.util.List;

public abstract class AbstractMongoRepository implements AutoCloseable {
    private ConnectionString connectionString = new ConnectionString("mongodb://mongodb1:27017," +
            "mongodb2:27018, mongodb3:27019/?replicaSet=replica_set_single");
    private MongoCredential credentials = MongoCredential.createCredential("admin",
            "admin", "adminpassword".toCharArray());
    protected CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(
            PojoCodecProvider.builder()
//                    .register("org.example.model.dto.BankAccountDTO")
                    .register(BankAccountDTO.class, StandardAccountDTO.class, SavingAccountDTO.class, JuniorAccountDTO.class)
                    .register(TransactionDTO.class)
                    .automatic(true)
                    .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
                    .build());
    protected MongoClient mongoClient;
    protected MongoDatabase bankSystemDB;

    protected void initDbConnection() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credentials)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(
//                        CodecRegistries.fromCodecs(new CustomBankAccountDTOCodec(pojoCodecRegistry)),
                        CodecRegistries.fromCodecs(new UUIDCodec()),
                        MongoClientSettings.getDefaultCodecRegistry(),
                        pojoCodecRegistry
                ))
                .build();
        mongoClient = MongoClients.create(settings);
        bankSystemDB = mongoClient.getDatabase("bankSystemDB");
    }
    protected void createBankAccountsCollection() {
        ValidationOptions validationOptions = new ValidationOptions().validator(
                Document.parse("""
                        {
                         $jsonSchema: {
                            bsonType: "object",
                            required: ["balance", "client", "active", "creationDate", "closeDate"],
                            properties: {
                                closeDate: {
                                    bsonType: ["date", "null"],
                                    description: "can be a date or null"
                                },
                            }
                         }
                        }
                        """
                )
        ).validationAction(ValidationAction.ERROR);
        CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions().validationOptions(validationOptions);
        bankSystemDB.createCollection("bankAccounts", createCollectionOptions);
    }
}
