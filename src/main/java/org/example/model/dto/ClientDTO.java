package org.example.model.dto;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.AbstractEntity;
import org.example.model.clients.Client;

import java.time.LocalDate;
import java.util.UUID;

public class ClientDTO extends AbstractEntity {

    @BsonProperty("firstName")
    private String firstName;

    @BsonProperty("lastName")
    private String lastName;

    @BsonProperty("dateOfBirth")
    private LocalDate dateOfBirth;

    @BsonProperty("clientType")
    private Client.ClientTypes clientType;

    @BsonProperty("street")
    private String street;

    @BsonProperty("city")
    private String city;

    @BsonProperty("streetNumber")
    private String streetNumber;

    @BsonProperty("activeAccounts")
    private int activeAccounts;

    @BsonCreator
    public ClientDTO(@BsonProperty("_id") UUID id,
                     @BsonProperty("firstName") String firstName,
                     @BsonProperty("lastName") String lastName,
                     @BsonProperty("dateOfBirth") LocalDate dateOfBirth,
                     @BsonProperty("clientType") Client.ClientTypes clientType,
                     @BsonProperty("street") String street,
                     @BsonProperty("city") String city,
                     @BsonProperty("number") String streetNumber,
                     @BsonProperty("activeAccounts") int activeAccounts) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.clientType = clientType;
        this.street = street;
        this.city = city;
        this.streetNumber = streetNumber;
        this.activeAccounts = activeAccounts;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Client.ClientTypes getClientType() {
        return clientType;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public UUID getId() {
        return super.getEntityId();
    }

    public int getActiveAccounts() {
        return activeAccounts;
    }

    public void setActiveAccounts(int activeAccounts) {
        this.activeAccounts = activeAccounts;
    }

    public void setClientType(Client.ClientTypes clientType) {
        this.clientType = clientType;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreetNumber(String number) {
        this.streetNumber = number;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
