package org.example.model.repositories;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.BsonNull;
import org.bson.conversions.Bson;
import org.example.model.Transaction;
import org.example.model.dto.TransactionDTO;
import org.example.model.mappers.TransactionDTOMapper;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TransactionRepository extends AbstractMongoRepository {

    private MongoCollection<TransactionDTO> transactions;

    public TransactionRepository() {
        super();
        initDbConnection();
        this.transactions = bankSystemDB.getCollection("transactions", TransactionDTO.class);
    }

    public Transaction add(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        transactions.insertOne(TransactionDTOMapper.toDTO(transaction));
//        Bson filter = Filters.eq("sourceAccount._id", transaction.getSourceAccount().getId());
//        Bson setUpdate = Updates.set("sourceAccount.closeDate", BsonNull.VALUE);
//        transactions.updateOne(filter, setUpdate);
//
//        filter = Filters.eq("destinationAccount._id", transaction.getDestinationAccount().getId());
//        setUpdate = Updates.set("destinationAccount.closeDate", BsonNull.VALUE);
//        transactions.updateOne(filter, setUpdate);
        return transaction;
    }

    public List<Transaction> findAll() {
        return transactions.find().into(new ArrayList<>()).stream().map(TransactionDTOMapper::fromDTO).toList();
    }

    public Transaction findById(UUID id) {
        Bson filter = Filters.eq("_id", id);
        return TransactionDTOMapper.fromDTO(transactions.find(filter).first());
    }

    public void close() throws Exception {
//        this.em.close();
    }
}
