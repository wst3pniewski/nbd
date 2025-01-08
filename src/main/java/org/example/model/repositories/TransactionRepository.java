package org.example.model.repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import org.example.model.dao.TransactionDao;
import org.example.model.domain.Transaction;
import org.example.model.domain.accounts.BankAccountIds;
import org.example.model.mappers.TransactionMapper;
import org.example.model.mappers.TransactionMapperBuilder;


import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;


public class TransactionRepository implements Repository<Transaction>, AutoCloseable {

    private static CqlSession session;
    private static TransactionMapper transactionMapper;
    private static TransactionDao transactionDao;

    public void initSession() {
        session = CqlSession.builder().withKeyspace(BankAccountIds.BANK_ACCOUNTS_KEYSPACE)
                .addContactPoint(new InetSocketAddress("cassandra1", 9042))
                .addContactPoint(new InetSocketAddress("cassandra2", 9043))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("cassandra", "cassandrapassword")
                .build();
        CreateKeyspace createKeyspace = createKeyspace(CqlIdentifier.fromCql("bank_accounts"))
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true);
        SimpleStatement statement = createKeyspace.build();
        session.execute(statement);
        session.execute(SchemaBuilder.dropTable(BankAccountIds.BANK_ACCOUNTS_KEYSPACE, CqlIdentifier.fromCql("transactions")).ifExists().build());
        SimpleStatement createTransactions =
                SchemaBuilder.createTable(CqlIdentifier.fromCql("bank_accounts"), CqlIdentifier.fromCql("transactions"))
                        .ifNotExists()
                        .withPartitionKey(CqlIdentifier.fromCql("transaction_id"), DataTypes.UUID)
                        .withColumn(CqlIdentifier.fromCql("source_account"), DataTypes.UUID)
                        .withColumn(CqlIdentifier.fromCql("destination_account"), DataTypes.UUID)
                        .withColumn(CqlIdentifier.fromCql("amount"), DataTypes.DECIMAL)
                        .build();
        session.execute(createTransactions);
    }

    public TransactionRepository() {
        initSession();
        transactionMapper = new TransactionMapperBuilder(session).build();
        transactionDao = transactionMapper.transactionDao();
    }

    public void add(Transaction transaction) {
        if (transaction == null) {
            return;
        }
        transactionDao.create(transaction);
    }

    public List<Transaction> findAll() {
        return transactionDao.findAll().all();
    }

    public Transaction findById(UUID id) {
        return transactionDao.findById(id);
    }


    public void delete(UUID id) {
        Transaction transaction = transactionDao.findById(id);
        if (transaction == null) {
            return;
        }
        transactionDao.delete(transaction);
    }

    public void update(Transaction transaction) {
        return;
    }

    public void close() {
        session.close();
    }
}
