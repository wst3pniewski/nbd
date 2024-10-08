package org.example.model.clients;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.model.AbstractEntity;

@Entity
@Table(name = "clients")
@Access(AccessType.FIELD)
public class Client extends AbstractEntity {
    @Id
    @SequenceGenerator(
            name = "clientIdSequence",
            initialValue = 1
    )
    @GeneratedValue(generator = "clientIdSequence")
    private long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    @NotNull
    private ClientType clientType;

    public Client() {
    }

    public Client(String firstName, String lastName, ClientType clientType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.clientType = clientType;
    }

    public @NotEmpty String getFirstName() {
        return firstName;
    }

    public @NotEmpty String getLastName() {
        return lastName;
    }

    public @NotNull ClientType getClientType() {
        return clientType;
    }
}