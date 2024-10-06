package org.example.model.clients;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "clients")
@Access(AccessType.FIELD)
public class Client {
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
    @NotNull
    private ClientType clientType;

}
