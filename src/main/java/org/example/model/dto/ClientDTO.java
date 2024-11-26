package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.example.model.AbstractEntity;
import org.example.model.clients.Client;

import java.time.LocalDate;
import java.util.UUID;

public class ClientDTO extends AbstractEntity {

    @JsonProperty("firstName")
    @BsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    @BsonProperty("lastName")
    private String lastName;

    @JsonProperty("dateOfBirth")
    @BsonProperty("dateOfBirth")
    private LocalDate dateOfBirth;

    @JsonProperty("clientType")
    @BsonProperty("clientType")
    private Client.ClientTypes clientType;

    @JsonProperty("street")
    @BsonProperty("street")
    private String street;

    @JsonProperty("city")
    @BsonProperty("city")
    private String city;

    @JsonProperty("streetNumber")
    @BsonProperty("streetNumber")
    private String streetNumber;

    @JsonProperty("activeAccounts")
    @BsonProperty("activeAccounts")
    private int activeAccounts;

    @JsonCreator
    @JsonbCreator
    @BsonCreator
    public ClientDTO(@JsonProperty("id") @JsonbProperty("id") @BsonProperty("_id") UUID id,
                     @JsonProperty("firstName") @BsonProperty("firstName") String firstName,
                     @JsonProperty("lastName") @BsonProperty("lastName") String lastName,
                     @JsonProperty("dateOfBirth") @BsonProperty("dateOfBirth") LocalDate dateOfBirth,
                     @JsonProperty("clientType") @BsonProperty("clientType") Client.ClientTypes clientType,
                     @JsonProperty("street") @BsonProperty("street") String street,
                     @JsonProperty("city") @BsonProperty("city") String city,
                     @JsonProperty("number") @BsonProperty("number") String streetNumber,
                     @JsonProperty("activeAccounts") @BsonProperty("activeAccounts") int activeAccounts) {
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

//    public ClientDTO(){
//        super();
//    }

//    @JsonbCreator
//    public ClientDTO(@JsonbProperty("_id") UUID id,
//                     @JsonbProperty("firstName") String firstName,
//                     @JsonbProperty("lastName") String lastName,
//                     @JsonbProperty("dateOfBirth") LocalDate dateOfBirth,
//                     @JsonbProperty("clientType") Client.ClientTypes clientType,
//                     @JsonbProperty("street") String street,
//                     @JsonbProperty("city") String city,
//                     @JsonbProperty("number") String streetNumber,
//                     @JsonbProperty("activeAccounts") int activeAccounts) {
//        super(id);
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.dateOfBirth = dateOfBirth;
//        this.clientType = clientType;
//        this.street = street;
//        this.city = city;
//        this.streetNumber = streetNumber;
//        this.activeAccounts = activeAccounts;
//    }

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
