package org.example.model.clients;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.model.AbstractEntity;

@Entity
@Table(name = "clients")
@Access(AccessType.FIELD)
public class Client extends AbstractEntity {
    public enum ClientTypes {
        STANDARD(2),
        ADVANCED(5),
        BUSINESS(7);

        int maxActiveAccounts;

        private ClientTypes(int maxActiveAccounts){
            this.maxActiveAccounts = maxActiveAccounts;
        }
    }

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

    @NotNull
    @Min(13)
    private int age;

    @Embedded
    private Address address;


//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    @NotNull
//    @JoinColumn(name = "client_type_id", nullable = false)
    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    private ClientTypes clientType;

    public Client() {
    }

    public Client(String firstName, String lastName, int age, ClientTypes clientType, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.clientType = clientType;
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(@NotEmpty int age) {
        this.age = age;
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
}