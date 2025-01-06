package org.example.model.domain.clients;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;

import java.time.LocalDate;
import java.util.UUID;

@Entity(defaultKeyspace = "bank_accounts")
@CqlName("clients")
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
public class Client {

    public enum ClientTypes {
        STANDARD(2), ADVANCED(5), BUSINESS(7);

        final int maxActiveAccounts;

        ClientTypes(int maxActiveAccounts) {
            this.maxActiveAccounts = maxActiveAccounts;
        }

        public int getMaxActiveAccounts() {
            return maxActiveAccounts;
        }
    }

    @PartitionKey
    @CqlName("client_id")
    private final UUID clientId;

    @ClusteringColumn
    @CqlName("client_type")
    private String clientType;

    private final String firstName;

    private final String lastName;

    private final LocalDate dateOfBirth;


    private String street;

    private String city;

    private String streetNumber;

//    private int activeAccounts;

//    public Client(String firstName,
//                  String lastName,
//                  LocalDate dateOfBirth,
//                  String clientType,
//                  String street,
//                  String city,
//                  String number) {
//        this.clientId = UUID.randomUUID();
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.dateOfBirth = dateOfBirth;
//        this.clientType = clientType;
//        this.street = street;
//        this.city = city;
//        this.streetNumber = number;
//        this.activeAccounts = 0;
//    }

    public Client(UUID clientId,
                  String clientType,
                  LocalDate dateOfBirth,
                  String firstName,
                  String lastName,
                  String street,
                  String city,
                  String number/*int activeAccounts*/) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.clientType = clientType;
        this.street = street;
        this.city = city;
        this.streetNumber = number;
//        this.activeAccounts = activeAccounts;
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

//    public void setActiveAccounts(int activeAccounts) {
//        this.activeAccounts = activeAccounts;
//    }

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

//    public int getActiveAccounts() {
//        return activeAccounts;
//    }
}