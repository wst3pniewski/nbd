package org.example.model.clients;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.AbstractEntity;

import java.time.LocalDate;
import java.util.UUID;

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

//    @BsonProperty("address")
//    private Address address;

    @BsonProperty("clientType")
    private ClientTypes clientType;

    @BsonProperty("street")
    private String street;

    @BsonProperty("city")
    private String city;

    @BsonProperty("streetNumber")
    private String number;

    @BsonCreator
    public Client(@BsonProperty("_id") UUID id,
                  @BsonProperty("firstName") String firstName,
                  @BsonProperty("lastName") String lastName,
                  @BsonProperty("dateOfBirth") LocalDate dateOfBirth,
                  @BsonProperty("clientType") ClientTypes clientType,
                  @BsonProperty("street") String street,
                  @BsonProperty("city") String city,
                  @BsonProperty("number") String number) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.clientType = clientType;
        this.street = street;
        this.city = city;
        this.number = number;
    }

    public Client(String firstName,
                  String lastName,
                  LocalDate dateOfBirth,
                  ClientTypes clientType,
                  String street,
                  String city,
                  String number) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.clientType = clientType;
        this.street = street;
        this.city = city;
        this.number = number;
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

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getStreetNumber() {
        return number;
    }

//    public long getId() {
//        return getEntityId();
//    }

    public UUID getId() {
        return getEntityId();
    }

}