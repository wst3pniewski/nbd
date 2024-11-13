package org.example.model.clients;

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

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private ClientTypes clientType;

    private String street;

    private String city;

    private String streetNumber;

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
        this.streetNumber = number;
    }

    public Client(UUID id,
                  String firstName,
                  String lastName,
                  LocalDate dateOfBirth,
                  ClientTypes clientType,
                  String street,
                  String city,
                  String number) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.clientType = clientType;
        this.street = street;
        this.city = city;
        this.streetNumber = number;
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
        return streetNumber;
    }

    public UUID getId() {
        return getEntityId();
    }

}