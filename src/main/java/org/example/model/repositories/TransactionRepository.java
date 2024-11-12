package org.example.model.repositories;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.example.model.Transaction;
import org.example.model.accounts.BankAccount;


import java.util.ArrayList;
import java.util.List;


public class TransactionRepository extends AbstractMongoRepository {

    private MongoCollection<Transaction> transactions;

    public TransactionRepository() {
        super();
        initDbConnection();
        this.transactions = bankSystemDB.getCollection("transactions", Transaction.class);
    }

    public Transaction add(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        transactions.insertOne(transaction);
        return transaction;
    }

    public List<Transaction> findAll() {
        return transactions.find().into(new ArrayList<>());
    }

    public Transaction findById(Long id) {
        Bson filter = Filters.eq("_id", id);
        return transactions.find(filter).first();
    }

    public void close() throws Exception {
//        this.em.close();
    }
}
