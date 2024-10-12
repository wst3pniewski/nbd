package org.example.model.clients;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.model.AbstractEntity;
import org.example.model.accounts.BankAccount;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clients")
@Access(AccessType.FIELD)
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
    private LocalDate dateOfBirth;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    private ClientTypes clientType;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<BankAccount> bankAccounts = new ArrayList<>();

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

    public void addAccount(BankAccount account) {
        bankAccounts.add(account);
    }

    public long getId() {
        return id;
    }
}