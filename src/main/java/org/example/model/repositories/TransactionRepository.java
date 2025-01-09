package org.example.model.repositories;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import org.example.model.dao.TransactionDao;
import org.example.model.domain.Transaction;
import org.example.model.domain.TransactionIds;
import org.example.model.domain.accounts.BankAccountIds;
import org.example.model.mappers.TransactionMapper;
import org.example.model.mappers.TransactionMapperBuilder;


import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
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

        // KEYSPACE
        CreateKeyspace createKeyspace = createKeyspace(TransactionIds.BANK_ACCOUNTS_KEYSPACE)
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true);
        SimpleStatement statement = createKeyspace.build();
        session.execute(statement);
//        session.execute(SchemaBuilder.dropTable(BankAccountIds.BANK_ACCOUNTS_KEYSPACE, TransactionIds.TRANSACTIONS).ifExists().build());

        // TRANSACTIONS
        SimpleStatement createTransactions =
                SchemaBuilder.createTable(TransactionIds.BANK_ACCOUNTS_KEYSPACE, TransactionIds.TRANSACTIONS)
                        .ifNotExists()
                        .withPartitionKey(TransactionIds.TRANSACTION_ID, DataTypes.UUID)
                        .withColumn(TransactionIds.SOURCE_ACCOUNT, DataTypes.UUID)
                        .withColumn(TransactionIds.DESTINATION_ACCOUNT, DataTypes.UUID)
                        .withColumn(TransactionIds.AMOUNT, DataTypes.DECIMAL)
                        .build();
        session.execute(createTransactions);

        // TRANSACTIONS_BY_SOURCE_ACCOUNT
        SimpleStatement createTransactionsBySourceAccount =
                SchemaBuilder.createTable(TransactionIds.BANK_ACCOUNTS_KEYSPACE, TransactionIds.TRANSACTIONS_BY_SOURCE_ACCOUNT)
                        .ifNotExists()
                        .withPartitionKey(TransactionIds.SOURCE_ACCOUNT, DataTypes.UUID)
                        .withClusteringColumn(TransactionIds.TRANSACTION_ID, DataTypes.UUID)
                        .withColumn(TransactionIds.DESTINATION_ACCOUNT, DataTypes.UUID)
                        .withColumn(TransactionIds.AMOUNT, DataTypes.DECIMAL)
                        .build();
        session.execute(createTransactionsBySourceAccount);

        // TRANSACTIONS_BY_DESTINATION_ACCOUNT
        SimpleStatement createTransactionsByDestinationAccount =
                SchemaBuilder.createTable(TransactionIds.BANK_ACCOUNTS_KEYSPACE, TransactionIds.TRANSACTIONS_BY_DESTINATION_ACCOUNT)
                        .ifNotExists()
                        .withPartitionKey(TransactionIds.DESTINATION_ACCOUNT, DataTypes.UUID)
                        .withClusteringColumn(TransactionIds.TRANSACTION_ID, DataTypes.UUID)
                        .withColumn(TransactionIds.SOURCE_ACCOUNT, DataTypes.UUID)
                        .withColumn(TransactionIds.AMOUNT, DataTypes.DECIMAL)
                        .build();
        session.execute(createTransactionsByDestinationAccount );
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
        insertTransactionBySourceAccount(transaction);
        insertTransactionByDestinationAccount(transaction);
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
        deleteTransactionBySourceAccount(transaction.getSourceAccount(), transaction.getId());
        deleteTransactionByDestinationAccount(transaction.getDestinationAccount(), transaction.getId());
    }

    private void insertTransactionByDestinationAccount(Transaction transaction) {
        Insert insert = QueryBuilder.insertInto(TransactionIds.BANK_ACCOUNTS_KEYSPACE, TransactionIds.TRANSACTIONS_BY_DESTINATION_ACCOUNT)
                .value(TransactionIds.DESTINATION_ACCOUNT, literal(transaction.getDestinationAccount()))
                .value(TransactionIds.TRANSACTION_ID, literal(transaction.getId()))
                .value(TransactionIds.SOURCE_ACCOUNT, literal(transaction.getSourceAccount()))
                .value(TransactionIds.AMOUNT, literal(transaction.getAmount()));
        session.execute(insert.build());
    }

    private void deleteTransactionByDestinationAccount(UUID destinationAccount, UUID transactionId) {
        SimpleStatement delete = QueryBuilder.deleteFrom(TransactionIds.BANK_ACCOUNTS_KEYSPACE, TransactionIds.TRANSACTIONS_BY_DESTINATION_ACCOUNT)
                .whereColumn(TransactionIds.DESTINATION_ACCOUNT).isEqualTo(literal(destinationAccount))
                .whereColumn(TransactionIds.TRANSACTION_ID).isEqualTo(literal(transactionId))
                .build();
        session.execute(delete);
    }

    public List<Transaction> findTransactionByDestinationAccount(UUID destinationAccount) {
        SimpleStatement select = QueryBuilder.selectFrom(TransactionIds.BANK_ACCOUNTS_KEYSPACE, TransactionIds.TRANSACTIONS_BY_DESTINATION_ACCOUNT)
                .all()
                .whereColumn(TransactionIds.DESTINATION_ACCOUNT).isEqualTo(literal(destinationAccount))
                .build();
        ResultSet resultSet = session.execute(select);
        List<Row> result = resultSet.all();
        return result.stream().map(this::getTransaction).toList();
    }

    private void insertTransactionBySourceAccount(Transaction transaction) {
        Insert insert = QueryBuilder.insertInto(TransactionIds.BANK_ACCOUNTS_KEYSPACE, TransactionIds.TRANSACTIONS_BY_SOURCE_ACCOUNT)
                .value(TransactionIds.SOURCE_ACCOUNT, literal(transaction.getSourceAccount()))
                .value(TransactionIds.TRANSACTION_ID, literal(transaction.getId()))
                .value(TransactionIds.DESTINATION_ACCOUNT, literal(transaction.getDestinationAccount()))
                .value(TransactionIds.AMOUNT, literal(transaction.getAmount()));
        session.execute(insert.build());
    }

    private void deleteTransactionBySourceAccount(UUID sourceAccount, UUID transactionId) {
        SimpleStatement delete = QueryBuilder.deleteFrom(TransactionIds.BANK_ACCOUNTS_KEYSPACE, TransactionIds.TRANSACTIONS_BY_SOURCE_ACCOUNT)
                .whereColumn(TransactionIds.SOURCE_ACCOUNT).isEqualTo(literal(sourceAccount))
                .whereColumn(TransactionIds.TRANSACTION_ID).isEqualTo(literal(transactionId))
                .build();
        session.execute(delete);
    }

    public List<Transaction> findTransactionBySourceAccount(UUID sourceAccount) {
        SimpleStatement select = QueryBuilder.selectFrom(TransactionIds.BANK_ACCOUNTS_KEYSPACE, TransactionIds.TRANSACTIONS_BY_SOURCE_ACCOUNT)
                .all()
                .whereColumn(TransactionIds.SOURCE_ACCOUNT).isEqualTo(literal(sourceAccount))
                .build();
        ResultSet resultSet = session.execute(select);
        List<Row> result = resultSet.all();
        return result.stream().map(this::getTransaction).toList();
    }

    private Transaction getTransaction(Row transaction) {
        return new Transaction(
                transaction.getUuid(TransactionIds.TRANSACTION_ID),
                transaction.getUuid(TransactionIds.SOURCE_ACCOUNT),
                transaction.getUuid(TransactionIds.DESTINATION_ACCOUNT),
                transaction.getBigDecimal(TransactionIds.AMOUNT)
        );
    }
    public void update(Transaction transaction) {
        return;
    }

    public void close() {
        session.close();
    }
}
