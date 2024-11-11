package org.example.model.clients;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.AbstractEntity;

import java.time.LocalDate;

public class Client extends AbstractEntity {

    public enum ClientTypes {
        STANDARD(2),
        ADVANCED(5),
        BUSINESS(7);

        int maxActiveAccounts;

        private ClientTypes(int maxActiveAccounts) {
            this.maxActiveAccounts = maxActiveAccounts;
        }

        public int getMaxActiveAccounts() {
            return maxActiveAccounts;
        }
    }

    @BsonProperty("firstName")
    private String firstName;

    @BsonProperty("lastName")
    private String lastName;

    @BsonProperty("dateOfBirth")
    private LocalDate dateOfBirth;

    @BsonProperty("address")
    private Address address;

    @BsonProperty("clientType")
    private ClientTypes clientType;

    @BsonCreator
    public Client(@BsonProperty("_id") long id,
                  @BsonProperty("firstName") String firstName,
                  @BsonProperty("lastName") String lastName,
                  @BsonProperty("dateOfBirth") LocalDate dateOfBirth,
                  @BsonProperty("clientType") ClientTypes clientType,
                  @BsonProperty("address") Address address) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.clientType = clientType;
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ClientTypes getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypes clientType) {
        this.clientType = clientType;
    }

    public long getId() {
        return getEntityId();
    }

}