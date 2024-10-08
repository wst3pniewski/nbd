package org.example.model.clients;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class BusinessClientType extends ClientType {


//    private BusinessClientType() {
//        super(10, "bussiness");
//    }

    public BusinessClientType() {
        super(10, "bussiness");
    }


}
