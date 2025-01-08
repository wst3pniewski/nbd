package org.example.model.domain.clients;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;

import java.time.LocalDate;
import java.util.UUID;

@Entity(defaultKeyspace = "bank_accounts")
@CqlName("clients")
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
public class Client {
    public static final String STANDARD = "STANDARD";
    public static final String ADVANCED = "ADVANCED";
    public static final String BUSINESS = "BUSINESS";

    @PartitionKey
    @CqlName("client_id")
    private final UUID clientId;

    @CqlName("client_type")
    private String clientType;

    private final String firstName;

    private final String lastName;

    private final LocalDate dateOfBirth;

    private String street;

    private String city;

    private String streetNumber;

    public Client(UUID clientId,
                  LocalDate dateOfBirth,
                  String firstName,
                  String lastName,
                  String clientType,
                  String street,
                  String city,
                  String streetNumber) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.clientType = clientType;
        this.street = street;
        this.city = city;
        this.streetNumber = streetNumber;
    }

    public UUID getClientId() {
        return clientId;
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

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public void setAddress(String street, String city, String number) {
        this.street = street;
        this.city = city;
        this.streetNumber = number;
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
}