package org.example.model.repositories;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
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
        InsertOneResult insertOneResult = transactions.insertOne(TransactionDTOMapper.toDTO(transaction));
        if (insertOneResult.wasAcknowledged() == false) {
            return null;
        }
        return transaction;
    }

    public List<Transaction> findAll() {
        return transactions.find().into(new ArrayList<>()).stream().map(TransactionDTOMapper::fromDTO).toList();
    }

    public Transaction findById(UUID id) {
        Bson filter = Filters.eq("_id", id);
        TransactionDTO transaction = transactions.find(filter).first();
        if (transaction == null) {
            return null;
        }
        return TransactionDTOMapper.fromDTO(transaction);
    }

    public Boolean delete(UUID id) {
        Bson filter = Filters.eq("_id", id);
        DeleteResult deleteResult = transactions.deleteOne(filter);
        if (deleteResult.getDeletedCount() == 0) {
            return false;
        }
        return true;
    }

    public void close() throws Exception {
//        this.em.close();
    }
}
