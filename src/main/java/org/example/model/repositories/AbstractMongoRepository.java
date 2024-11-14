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
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.clients.Client;
import org.example.model.codecs.UUIDCodec;
import org.example.model.dto.*;

import java.time.LocalDate;
import java.util.List;

public abstract class AbstractMongoRepository implements AutoCloseable {
    private final ConnectionString connectionString = new ConnectionString("mongodb://mongodb1:27017," +
            "mongodb2:27018, mongodb3:27019/?replicaSet=replica_set_single");
    private final MongoCredential credentials = MongoCredential.createCredential("admin",
            "admin", "adminpassword".toCharArray());
    protected CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(
            PojoCodecProvider.builder()
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
                        CodecRegistries.fromCodecs(new UUIDCodec()),
                        MongoClientSettings.getDefaultCodecRegistry(),
                        pojoCodecRegistry
                ))
                .build();
        mongoClient = MongoClients.create(settings);
        bankSystemDB = mongoClient.getDatabase("bankSystemDB");
    }

    protected void createOrUpdateBankAccountsCollection() {
        ValidationOptions validationOptions = new ValidationOptions().validator(
                Document.parse("""
                        {
                          $jsonSchema: {
                            bsonType: 'object',
                            required: [
                              'balance',
                              'client',
                              'active',
                              'creationDate'
                            ],
                            properties: {
                              balance: {
                                bsonType: 'decimal',
                                minimum: 0,
                                description: 'Balance must be a non-negative number'
                              },
                              closeDate: {
                                bsonType: [
                                  'date',
                                  'null'
                                ],
                                description: 'can be a date or null'
                              },
                              active: {
                                bsonType: 'bool',
                                description: 'Status must be a boolean (true or false)'
                              }
                            }
                          }
                        }
                        """
                )
        ).validationAction(ValidationAction.ERROR);
        CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions().validationOptions(validationOptions);
        try {
            bankSystemDB.createCollection("bankAccounts", createCollectionOptions);
        } catch (Exception e) {
            bankSystemDB.runCommand(new Document("collMod", "bankAccounts").append("validator", validationOptions.getValidator()).append("validationLevel", "strict").append("validationAction", "error"));
        }
    }

    protected void createOrUpdateClientsCollection() {
        ValidationOptions validationOptions = new ValidationOptions().validator(
                Document.parse(""" 
                        { $jsonSchema: {
                              bsonType: 'object',
                              "required": [
                                  "firstName",
                                  "lastName",
                                  "dateOfBirth",
                                  "clientType",
                                  "street",
                                  "city",
                                  "streetNumber",
                                  "activeAccounts"
                              ],
                              properties: {
                                firstName: {
                                  bsonType: 'string',
                                  maxLength: 10,
                                  description: 'The client\\'s first name'
                                },
                                lastName: {
                                  bsonType: 'string',
                                  maxLength: 15,
                                  description: 'The client\\'s last name'
                                },
                                dateOfBirth: {
                                  bsonType: 'date',
                                  description: 'The client\\'s date of birth'
                                },
                                clientType: {
                                  bsonType: 'string',
                                  'enum': [
                                    'STANDARD',
                                    'ADVANCED',
                                    'BUSINESS'
                                  ],
                                  description: 'The type of client'
                                },
                                street: {
                                  bsonType: 'string',
                                  maxLength: 15,
                                  description: 'The client\\'s street address'
                                },
                                city: {
                                  bsonType: 'string',
                                  maxLength: 15,
                                  description: 'The client\\'s city'
                                },
                                streetNumber: {
                                  bsonType: 'string',
                                  maxLength: 6,
                                  description: 'The client\\'s street number'
                                },
                                activeAccounts: {
                                  bsonType: 'int',
                                  minimum: 0,
                                  description: 'The number of active accounts the client has'
                                }
                              },
                          }
                        }
                        """)
        ).validationAction(ValidationAction.ERROR);

        CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions().validationOptions(validationOptions);
        try {
            bankSystemDB.createCollection("clients", createCollectionOptions);
        } catch (Exception e) {
            bankSystemDB.runCommand(new Document("collMod", "clients").append("validator", validationOptions.getValidator()).append("validationLevel", "strict").append("validationAction", "error"));
        }
    }

    protected void createOrUpdateTransactionsCollection() {
        ValidationOptions validationOptions = new ValidationOptions().validator(
                Document.parse("""
                        {
                          $jsonSchema: {
                            bsonType: 'object',
                            required: [
                              'sourceAccount',
                              'destinationAccount',
                              'amount'
                            ],
                            properties: {
                              sourceAccount: {
                                bsonType: 'string'
                              },
                              destinationAccount: {
                                bsonType: 'string'
                              },
                              amount: {
                                bsonType: 'decimal',
                                minimum: 1,
                                description: 'must be a greater or equal to 1'
                              }
                            }
                          }
                        }
                        """)
        ).validationAction(ValidationAction.ERROR);

        CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions().validationOptions(validationOptions);
        try {
            bankSystemDB.createCollection("transactions", createCollectionOptions);
        } catch (Exception e) {
            bankSystemDB.runCommand(new Document("collMod", "transactions").append("validator", validationOptions.getValidator()).append("validationLevel", "strict").append("validationAction", "error"));
        }
    }
}
