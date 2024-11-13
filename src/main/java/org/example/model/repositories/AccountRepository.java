package org.example.model.repositories;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.BsonNull;
import org.bson.conversions.Bson;
import org.example.model.accounts.BankAccount;
import org.example.model.accounts.JuniorAccount;
import org.example.model.accounts.SavingAccount;
import org.example.model.accounts.StandardAccount;
import org.example.model.clients.Client;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AccountRepository extends AbstractMongoRepository {

    private MongoCollection<BankAccount> bankAccounts;

    public AccountRepository() {
        super();
        initDbConnection();
//        createBankAccountsCollection();
        this.bankAccounts = bankSystemDB.getCollection("bankAccounts", BankAccount.class);
    }

    public BankAccount add(BankAccount account) {
        if (account == null) {
            return null;
        }
        bankAccounts.insertOne(account);

        Bson filter = Filters.eq("_id", account.getId());
        Bson setUpdate = Updates.set("closeDate", BsonNull.VALUE);
        bankAccounts.updateOne(filter, setUpdate);
        return account;
    }

    public List<BankAccount> findAll() {
        return bankAccounts.find().into(new ArrayList<>());
    }


    public BankAccount findById(UUID id) {
        Bson filter = Filters.eq("_id", id);
        BankAccount bankAccount = bankAccounts.find(filter).first();
        return bankAccount;
    }


    public BankAccount update(BankAccount account) {
        if (account == null) {
            return null;
        }
        Bson filter = Filters.eq("_id", account.getId());
        Bson setUpdate = null;
        if (account instanceof StandardAccount) {
            setUpdate = Updates.combine(
                    Updates.set("balance", account.getBalance()),
                    Updates.set("active", account.getActive()),
                    Updates.set("closeDate", account.getCloseDate()),
                    Updates.set("debit", ((StandardAccount) account).getDebit()),
                    Updates.set("debitLimit", ((StandardAccount) account).getDebitLimit())
            );
        } else if (account instanceof SavingAccount) {
            setUpdate = Updates.combine(
                    Updates.set("balance", account.getBalance()),
                    Updates.set("active", account.getActive()),
                    Updates.set("closeDate", account.getCloseDate()),
                    Updates.set("interestRate", ((SavingAccount) account).getInterestRate())
            );
        } else if (account instanceof JuniorAccount) {
            setUpdate = Updates.combine(
                    Updates.set("balance", account.getBalance()),
                    Updates.set("active", account.getActive()),
                    Updates.set("closeDate", account.getCloseDate())
            );
        }

        bankAccounts.updateOne(filter, setUpdate);
        return account;
    }

    public List<BankAccount> getAccountsByClientId(UUID clientId) {
        Bson filter = Filters.eq("client._id", clientId);
        return bankAccounts.find(filter).into(new ArrayList<>());
    }


    public long countActiveByClientId(UUID clientId) {
        Bson filter = Filters.and(
                Filters.eq("client._id", clientId),
                Filters.eq("active", true)
        );
        return bankAccounts.countDocuments(filter);
    }


    public void close() throws Exception {
//        this.em.close();
    }
}
