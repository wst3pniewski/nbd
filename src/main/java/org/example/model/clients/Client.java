package org.example.model.clients;

import org.example.model.AbstractEntity;

import java.time.LocalDate;
import java.util.UUID;

public class Client extends AbstractEntity {

    public enum ClientTypes {
        STANDARD(2),
        ADVANCED(5),
        BUSINESS(7);

        final int maxActiveAccounts;

        ClientTypes(int maxActiveAccounts) {
            this.maxActiveAccounts = maxActiveAccounts;
        }

        public int getMaxActiveAccounts() {
            return maxActiveAccounts;
        }
    }

    private final String firstName;

    private final String lastName;

    private final LocalDate dateOfBirth;

    private ClientTypes clientType;

    private String street;

    private String city;

    private String streetNumber;

    private int activeAccounts;

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
        this.activeAccounts = 0;
    }

    public Client(UUID id,
                  String firstName,
                  String lastName,
                  LocalDate dateOfBirth,
                  ClientTypes clientType,
                  String street,
                  String city,
                  String number,
                  int activeAccounts) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.clientType = clientType;
        this.street = street;
        this.city = city;
        this.streetNumber = number;
        this.activeAccounts = activeAccounts;
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

    public void setActiveAccounts(int activeAccounts) {
        this.activeAccounts = activeAccounts;
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

    public UUID getId() {
        return getEntityId();
    }

    public int getActiveAccounts() {
        return activeAccounts;
    }
}