package org.example.model.redis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.example.model.AbstractEntity;
import org.example.model.clients.Client;

import java.time.LocalDate;
import java.util.UUID;

public class ClientRedis extends AbstractEntity {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("dateOfBirth")
    private LocalDate dateOfBirth;

    @JsonProperty("clientType")
    private Client.ClientTypes clientType;

    @JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    @JsonProperty("streetNumber")
    private String streetNumber;

    @JsonProperty("activeAccounts")
    private int activeAccounts;

    @JsonCreator
    @JsonbCreator
    public ClientRedis(@JsonProperty("id") @JsonbProperty("id") UUID id,
                     @JsonProperty("firstName") String firstName,
                     @JsonProperty("lastName") String lastName,
                     @JsonProperty("dateOfBirth") LocalDate dateOfBirth,
                     @JsonProperty("clientType") Client.ClientTypes clientType,
                     @JsonProperty("street") String street,
                     @JsonProperty("city") String city,
                     @JsonProperty("number") String streetNumber,
                     @JsonProperty("activeAccounts") int activeAccounts) {
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