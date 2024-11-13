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

//    @BsonProperty("address")
//    private Address address;

    @BsonProperty("clientType")
    private Client.ClientTypes clientType;

    @BsonProperty("street")
    private String street;

    @BsonProperty("city")
    private String city;

    @BsonProperty("streetNumber")
    private String number;

    @BsonCreator
    public ClientDTO(@BsonProperty("_id") UUID id,
                  @BsonProperty("firstName") String firstName,
                  @BsonProperty("lastName") String lastName,
                  @BsonProperty("dateOfBirth") LocalDate dateOfBirth,
                  @BsonProperty("clientType") Client.ClientTypes clientType,
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
}
