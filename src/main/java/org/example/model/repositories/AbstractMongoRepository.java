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

import java.util.List;

public abstract class AbstractMongoRepository implements AutoCloseable {
    private ConnectionString connectionString = new ConnectionString("mongodb://mongodb1:27017," +
            "mongodb2:27018, mongodb3:27019/?replicaSet=replica_set_single");
    private MongoCredential credentials = MongoCredential.createCredential("admin",
            "admin", "adminpassword".toCharArray());
    private CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(
            PojoCodecProvider.builder()
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
                            required: ["balance", "client", "active", "creationDate"],
                            properties: {
                                _clazz: {
                                            enum: ["StandardAccount", "SavingAccount", "JuniorAccount"]
                                        },
                                        debitLimit: {
                                            bsonType: "decimal",
                                            description: "must be a decimal and is required if _clazz is StandardAccount"
                                        },
                                        interestRate: {
                                            bsonType: "decimal",
                                            description: "must be a decimal and is required if _clazz is SavingAccount"
                                        },
                                        parent: {
                                            bsonType: "object",
                                            description: "must be an objectId and is required if _clazz is JuniorAccount"
                                        },
                                        balance: {
                                            bsonType: "decimal",
                                            description: "must be a decimal and is required"
                                        }
                                },
                                if: { properties: { _clazz: { const: "StandardAccount" } } },
                                    then: { required: ["debitLimit"] },
                                    else: {
                                        if: { properties: { _clazz: { const: "SavingAccount" } } },
                                        then: { required: ["interestRate"] },
                                        else: {
                                            if: { properties: { _clazz: { const: "JuniorAccount" } } },
                                            then: { required: ["parentId"] }
                                        }
                                    }
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
