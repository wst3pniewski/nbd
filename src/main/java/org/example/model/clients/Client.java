package org.example.model.clients;

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

    private long id;


    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private Address address;

    private ClientTypes clientType;

    public Client() {
    }

    public Client(String firstName, String lastName, LocalDate dateOfBirth, ClientTypes clientType, Address address) {
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
        return id;
    }

}