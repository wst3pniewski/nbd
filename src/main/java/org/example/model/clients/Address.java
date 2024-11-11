package org.example.model.clients;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class Address {
    @BsonProperty("street")
    private String street;

    @BsonProperty("city")
    private String city;

    @BsonProperty("number")
    private String number;
    @BsonCreator
    public Address(@BsonProperty("street") String street,
                   @BsonProperty("city") String city,
                   @BsonProperty("number") String number) {
        this.street = street;
        this.city = city;
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getNumber() {
        return number;
    }
}
