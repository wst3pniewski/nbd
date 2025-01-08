package org.example.model.repositories;


import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.update.Update;
import org.example.model.BankAccountQueryProvider;
import org.example.model.dao.BankAccountDao;
import org.example.model.dao.JuniorAccountDao;
import org.example.model.dao.SavingAccountDao;
import org.example.model.dao.StandardAccountDao;
import org.example.model.domain.accounts.*;
import org.example.model.mappers.BankAccountMapper;
import org.example.model.mappers.BankAccountMapperBuilder;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;


public class AccountRepository implements Repository<BankAccount> {

    private static CqlSession session;
    private static BankAccountMapper accountMapper;
    private static BankAccountDao bankAccountDao;

    public void initSession() {
        session = CqlSession.builder().withKeyspace("bank_accounts")
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

        SimpleStatement createBankAccounts =
                SchemaBuilder.createTable(BankAccountIds.BANK_ACCOUNTS_KEYSPACE, BankAccountIds.BANK_ACCOUNT_TABLE)
                        .ifNotExists()
                        .withPartitionKey(BankAccountIds.ACCOUNT_ID, DataTypes.UUID)
                        .withColumn(BankAccountIds.BALANCE, DataTypes.DECIMAL)
                        .withColumn(BankAccountIds.CLIENT_ID, DataTypes.UUID)
                        .withColumn(BankAccountIds.IS_ACTIVE, DataTypes.BOOLEAN)
                        .withColumn(BankAccountIds.CREATION_DATE, DataTypes.DATE)
                        .withColumn(BankAccountIds.CLOSE_DATE, DataTypes.DATE)
                        .withColumn(BankAccountIds.DISCRIMINATOR, DataTypes.TEXT)
                        .withColumn(BankAccountIds.DEBIT_LIMIT, DataTypes.DECIMAL)
                        .withColumn(BankAccountIds.DEBIT, DataTypes.DECIMAL)
                        .withColumn(BankAccountIds.INTEREST_RATE, DataTypes.DECIMAL)
                        .withColumn(BankAccountIds.PARENT_ID, DataTypes.UUID)
                        .build();
        session.execute(createBankAccounts);

        SimpleStatement createBankAccountsByClients =
                SchemaBuilder.createTable(BankAccountIds.BANK_ACCOUNTS_KEYSPACE, BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                        .ifNotExists()
                        .withPartitionKey(BankAccountIds.CLIENT_ID, DataTypes.UUID)
                        .withClusteringColumn(BankAccountIds.ACCOUNT_ID, DataTypes.UUID)
                        .withColumn(BankAccountIds.BALANCE, DataTypes.DECIMAL)
                        .withColumn(BankAccountIds.IS_ACTIVE, DataTypes.BOOLEAN)
                        .withColumn(BankAccountIds.CREATION_DATE, DataTypes.DATE)
                        .withColumn(BankAccountIds.CLOSE_DATE, DataTypes.DATE)
                        .withColumn(BankAccountIds.DISCRIMINATOR, DataTypes.TEXT)
                        .withColumn(BankAccountIds.DEBIT_LIMIT, DataTypes.DECIMAL)
                        .withColumn(BankAccountIds.DEBIT, DataTypes.DECIMAL)
                        .withColumn(BankAccountIds.INTEREST_RATE, DataTypes.DECIMAL)
                        .withColumn(BankAccountIds.PARENT_ID, DataTypes.UUID)
                        .build();
        session.execute(createBankAccountsByClients);
    }

    public AccountRepository() {
        initSession();
        accountMapper = new BankAccountMapperBuilder(session).build();
        bankAccountDao = accountMapper.bankAccountDao(BankAccountIds.BANK_ACCOUNTS_KEYSPACE);
    }

    public void add(BankAccount account) {
        if (account == null) {
            return;
        }
        bankAccountDao.create(account);
        insertBankAccountByClient(account);
    }

    public List<BankAccount> findAll() {
        return bankAccountDao.findAll();
    }


    public BankAccount findById(UUID id) {
        BankAccount account = bankAccountDao.findById(id);
        if (account == null) {
            return null;
        }
        return account;
    }

    public List<BankAccount> findByClientId(UUID clientId) {
        Select selectBankAccount = QueryBuilder
                .selectFrom(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                .all()
                .where(Relation.column(BankAccountIds.CLIENT_ID).isEqualTo(literal(clientId)));
        ResultSet resultSet = session.execute(selectBankAccount.build());
        List<Row> result = resultSet.all();
        List<BankAccount> bankAccounts = result.stream().map(row -> {
            String discriminator = row.getString(BankAccountIds.DISCRIMINATOR);
            return switch (discriminator) {
                case "JUNIOR" -> BankAccountQueryProvider.getJuniorAccount(row);
                case "SAVING" -> BankAccountQueryProvider.getSavingAccount(row);
                case "STANDARD" -> BankAccountQueryProvider.getStandardAccount(row);
                default -> throw new IllegalStateException("Unexpected value: " + discriminator);
            };
        }).toList();
        return bankAccounts;
    }

    private void insertBankAccountByClient(BankAccount bankAccount) {
        Insert insert = null;
        switch (bankAccount.getDiscriminator()) {
            case "JUNIOR" -> {
                insert = QueryBuilder.insertInto(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                        .value(BankAccountIds.CLIENT_ID, literal(bankAccount.getClientId()))
                        .value(BankAccountIds.ACCOUNT_ID, literal(bankAccount.getId()))
                        .value(BankAccountIds.BALANCE, literal(bankAccount.getBalance()))
                        .value(BankAccountIds.IS_ACTIVE, literal(bankAccount.getActive()))
                        .value(BankAccountIds.CREATION_DATE, literal(bankAccount.getCreationDate()))
                        .value(BankAccountIds.CLOSE_DATE, literal(bankAccount.getCloseDate()))
                        .value(BankAccountIds.DISCRIMINATOR, literal(bankAccount.getDiscriminator()))
                        // JUNIOR
                        .value(BankAccountIds.PARENT_ID, literal(((JuniorAccount) bankAccount).getParentId()))
                        .ifNotExists();
            }
            case "SAVING" -> {
                insert = QueryBuilder.insertInto(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                        .value(BankAccountIds.CLIENT_ID, literal(bankAccount.getClientId()))
                        .value(BankAccountIds.ACCOUNT_ID, literal(bankAccount.getId()))
                        .value(BankAccountIds.BALANCE, literal(bankAccount.getBalance()))
                        .value(BankAccountIds.IS_ACTIVE, literal(bankAccount.getActive()))
                        .value(BankAccountIds.CREATION_DATE, literal(bankAccount.getCreationDate()))
                        .value(BankAccountIds.CLOSE_DATE, literal(bankAccount.getCloseDate()))
                        .value(BankAccountIds.DISCRIMINATOR, literal(bankAccount.getDiscriminator()))
                        // SAVING
                        .value(BankAccountIds.INTEREST_RATE, literal(((SavingAccount) bankAccount).getInterestRate()))
                        .ifNotExists();
            }
            case "STANDARD" -> {
                insert = QueryBuilder.insertInto(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                        .value(BankAccountIds.CLIENT_ID, literal(bankAccount.getClientId()))
                        .value(BankAccountIds.ACCOUNT_ID, literal(bankAccount.getId()))
                        .value(BankAccountIds.BALANCE, literal(bankAccount.getBalance()))
                        .value(BankAccountIds.IS_ACTIVE, literal(bankAccount.getActive()))
                        .value(BankAccountIds.CREATION_DATE, literal(bankAccount.getCreationDate()))
                        .value(BankAccountIds.CLOSE_DATE, literal(bankAccount.getCloseDate()))
                        .value(BankAccountIds.DISCRIMINATOR, literal(bankAccount.getDiscriminator()))
                        // STANDARD
                        .value(BankAccountIds.DEBIT, literal(((StandardAccount) bankAccount).getDebit()))
                        .value(BankAccountIds.DEBIT_LIMIT, literal(((StandardAccount) bankAccount).getDebitLimit()))
                        .ifNotExists();
            }
            default -> throw new IllegalStateException("Unexpected value: " + bankAccount.getDiscriminator());
        }

        session.execute(insert.build());
    }

    public void update(BankAccount bankAccount, BankAccount bankAccount2) {
        if (bankAccount == null || bankAccount2 == null) {
            return;
        }

        // Account 1
        // General fields
        Update updateBankAccount = QueryBuilder.update(BankAccountIds.BANK_ACCOUNT_TABLE)
                .setColumn(BankAccountIds.BALANCE, literal(bankAccount.getBalance()))
                .setColumn(BankAccountIds.IS_ACTIVE, literal(bankAccount.getActive()))
                .setColumn(BankAccountIds.CLOSE_DATE, literal(bankAccount.getCloseDate()))
                .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));

        Update updateBankAccountByClient = QueryBuilder.update(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                .setColumn(BankAccountIds.BALANCE, literal(bankAccount.getBalance()))
                .setColumn(BankAccountIds.IS_ACTIVE, literal(bankAccount.getActive()))
                .setColumn(BankAccountIds.CLOSE_DATE, literal(bankAccount.getCloseDate()))
                .whereColumn(BankAccountIds.CLIENT_ID).isEqualTo(literal(bankAccount.getClientId()))
                .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));

        // Account 2
        // General fields
        Update updateBankAccount2 = QueryBuilder.update(BankAccountIds.BANK_ACCOUNT_TABLE)
                .setColumn(BankAccountIds.BALANCE, literal(bankAccount2.getBalance()))
                .setColumn(BankAccountIds.IS_ACTIVE, literal(bankAccount2.getActive()))
                .setColumn(BankAccountIds.CLOSE_DATE, literal(bankAccount2.getCloseDate()))
                .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount2.getId()));

        Update updateBankAccountByClient2 = QueryBuilder.update(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                .setColumn(BankAccountIds.BALANCE, literal(bankAccount2.getBalance()))
                .setColumn(BankAccountIds.IS_ACTIVE, literal(bankAccount2.getActive()))
                .setColumn(BankAccountIds.CLOSE_DATE, literal(bankAccount2.getCloseDate()))
                .whereColumn(BankAccountIds.CLIENT_ID).isEqualTo(literal(bankAccount2.getClientId()))
                .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount2.getId()));

        // Account 1
        // Specific fields
        Update updateBankAccountSpecific;
        Update updateBankAccountByClientSpecific;
        switch (bankAccount.getDiscriminator()) {
            case "JUNIOR" -> {
                updateBankAccountSpecific = QueryBuilder.update(BankAccountIds.BANK_ACCOUNT_TABLE)
                        .setColumn(BankAccountIds.PARENT_ID, literal(((JuniorAccount) bankAccount).getParentId()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));
                updateBankAccountByClientSpecific = QueryBuilder.update(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                        .setColumn(BankAccountIds.PARENT_ID, literal(((JuniorAccount) bankAccount).getParentId()))
                        .whereColumn(BankAccountIds.CLIENT_ID).isEqualTo(literal(bankAccount.getClientId()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));
            }
            case "SAVING" -> {
                updateBankAccountSpecific = QueryBuilder.update(BankAccountIds.BANK_ACCOUNT_TABLE)
                        .setColumn(BankAccountIds.INTEREST_RATE, literal(((SavingAccount) bankAccount).getInterestRate()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));
                updateBankAccountByClientSpecific = QueryBuilder.update(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                        .setColumn(BankAccountIds.INTEREST_RATE, literal(((SavingAccount) bankAccount).getInterestRate()))
                        .whereColumn(BankAccountIds.CLIENT_ID).isEqualTo(literal(bankAccount.getClientId()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));
            }
            case "STANDARD" -> {
                updateBankAccountSpecific = QueryBuilder.update(BankAccountIds.BANK_ACCOUNT_TABLE)
                        .setColumn(BankAccountIds.DEBIT, literal(((StandardAccount) bankAccount).getDebit())
                        )
                        .setColumn(BankAccountIds.DEBIT_LIMIT, literal(((StandardAccount) bankAccount).getDebitLimit()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));
                updateBankAccountByClientSpecific = QueryBuilder.update(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                        .setColumn(BankAccountIds.DEBIT, literal(((StandardAccount) bankAccount).getDebit())
                        )
                        .setColumn(BankAccountIds.DEBIT_LIMIT, literal(((StandardAccount) bankAccount).getDebitLimit())
                        )
                        .whereColumn(BankAccountIds.CLIENT_ID).isEqualTo(literal(bankAccount.getClientId()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));
            }
            default -> throw new IllegalStateException("Unexpected value: " + bankAccount.getDiscriminator());
        }

        // Account 2
        // Specific fields
        Update updateBankAccountSpecific2;
        Update updateBankAccountByClientSpecific2;
        switch (bankAccount2.getDiscriminator()) {
            case "JUNIOR" -> {
                updateBankAccountSpecific2 = QueryBuilder.update(BankAccountIds.BANK_ACCOUNT_TABLE)
                        .setColumn(BankAccountIds.PARENT_ID, literal(((JuniorAccount) bankAccount2).getParentId()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount2.getId()));
                updateBankAccountByClientSpecific2 = QueryBuilder.update(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                        .setColumn(BankAccountIds.PARENT_ID, literal(((JuniorAccount) bankAccount2).getParentId()))
                        .whereColumn(BankAccountIds.CLIENT_ID).isEqualTo(literal(bankAccount2.getClientId()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount2.getId()));
            }
            case "SAVING" -> {
                updateBankAccountSpecific2 = QueryBuilder.update(BankAccountIds.BANK_ACCOUNT_TABLE)
                        .setColumn(BankAccountIds.INTEREST_RATE, literal(((SavingAccount) bankAccount2).getInterestRate()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount2.getId()));
                updateBankAccountByClientSpecific2 = QueryBuilder.update(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                        .setColumn(BankAccountIds.INTEREST_RATE, literal(((SavingAccount) bankAccount2).getInterestRate()))
                        .whereColumn(BankAccountIds.CLIENT_ID).isEqualTo(literal(bankAccount2.getClientId()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount2.getId()));
            }
            case "STANDARD" -> {
                updateBankAccountSpecific2 = QueryBuilder.update(BankAccountIds.BANK_ACCOUNT_TABLE)
                        .setColumn(BankAccountIds.DEBIT, literal(((StandardAccount) bankAccount2).getDebit())
                        )
                        .setColumn(BankAccountIds.DEBIT_LIMIT, literal(((StandardAccount) bankAccount2).getDebitLimit()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount2.getId()));
                updateBankAccountByClientSpecific2 = QueryBuilder.update(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                        .setColumn(BankAccountIds.DEBIT, literal(((StandardAccount) bankAccount2).getDebit())
                        )
                        .setColumn(BankAccountIds.DEBIT_LIMIT, literal(((StandardAccount) bankAccount2).getDebitLimit())
                        )
                        .whereColumn(BankAccountIds.CLIENT_ID).isEqualTo(literal(bankAccount2.getClientId()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount2.getId()));
            }
            default -> throw new IllegalStateException("Unexpected value: " + bankAccount2.getDiscriminator());
        }

        BatchStatement batchStatement = BatchStatement.builder(BatchType.LOGGED)
                .addStatement(updateBankAccount.build())
                .addStatement(updateBankAccountByClient.build())
                .addStatement(updateBankAccountSpecific.build())
                .addStatement(updateBankAccountByClientSpecific.build())
                .addStatement(updateBankAccount2.build())
                .addStatement(updateBankAccountByClient2.build())
                .addStatement(updateBankAccountSpecific2.build())
                .addStatement(updateBankAccountByClientSpecific2.build())
                .build();
        session.execute(batchStatement);
    }

    public void update(BankAccount bankAccount) {
        if (bankAccount == null) {
            return;
        }

        // General fields
        Update updateBankAccount = QueryBuilder.update(BankAccountIds.BANK_ACCOUNT_TABLE)
                .setColumn(BankAccountIds.BALANCE, literal(bankAccount.getBalance()))
                .setColumn(BankAccountIds.IS_ACTIVE, literal(bankAccount.getActive()))
                .setColumn(BankAccountIds.CLOSE_DATE, literal(bankAccount.getCloseDate()))
                .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));

        Update updateBankAccountByClient = QueryBuilder.update(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                .setColumn(BankAccountIds.BALANCE, literal(bankAccount.getBalance()))
                .setColumn(BankAccountIds.IS_ACTIVE, literal(bankAccount.getActive()))
                .setColumn(BankAccountIds.CLOSE_DATE, literal(bankAccount.getCloseDate()))
                .whereColumn(BankAccountIds.CLIENT_ID).isEqualTo(literal(bankAccount.getClientId()))
                .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));

        // Specific fields
        Update updateBankAccountSpecific;
        Update updateBankAccountByClientSpecific;
        switch (bankAccount.getDiscriminator()) {
            case "JUNIOR" -> {
                updateBankAccountSpecific = QueryBuilder.update(BankAccountIds.BANK_ACCOUNT_TABLE)
                        .setColumn(BankAccountIds.PARENT_ID, literal(((JuniorAccount) bankAccount).getParentId()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));
                updateBankAccountByClientSpecific = QueryBuilder.update(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                        .setColumn(BankAccountIds.PARENT_ID, literal(((JuniorAccount) bankAccount).getParentId()))
                        .whereColumn(BankAccountIds.CLIENT_ID).isEqualTo(literal(bankAccount.getClientId()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));
            }
            case "SAVING" -> {
                updateBankAccountSpecific = QueryBuilder.update(BankAccountIds.BANK_ACCOUNT_TABLE)
                        .setColumn(BankAccountIds.INTEREST_RATE, literal(((SavingAccount) bankAccount).getInterestRate()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));
                updateBankAccountByClientSpecific = QueryBuilder.update(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                        .setColumn(BankAccountIds.INTEREST_RATE, literal(((SavingAccount) bankAccount).getInterestRate()))
                        .whereColumn(BankAccountIds.CLIENT_ID).isEqualTo(literal(bankAccount.getClientId()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));
            }
            case "STANDARD" -> {
                updateBankAccountSpecific = QueryBuilder.update(BankAccountIds.BANK_ACCOUNT_TABLE)
                        .setColumn(BankAccountIds.DEBIT, literal(((StandardAccount) bankAccount).getDebit())
                        )
                        .setColumn(BankAccountIds.DEBIT_LIMIT, literal(((StandardAccount) bankAccount).getDebitLimit()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));
                updateBankAccountByClientSpecific = QueryBuilder.update(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                        .setColumn(BankAccountIds.DEBIT, literal(((StandardAccount) bankAccount).getDebit())
                        )
                        .setColumn(BankAccountIds.DEBIT_LIMIT, literal(((StandardAccount) bankAccount).getDebitLimit())
                        )
                        .whereColumn(BankAccountIds.CLIENT_ID).isEqualTo(literal(bankAccount.getClientId()))
                        .whereColumn(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccount.getId()));
            }
            default -> throw new IllegalStateException("Unexpected value: " + bankAccount.getDiscriminator());
        }
        BatchStatement batchStatement = BatchStatement.builder(BatchType.LOGGED)
                .addStatement(updateBankAccount.build())
                .addStatement(updateBankAccountByClient.build())
                .addStatement(updateBankAccountSpecific.build())
                .addStatement(updateBankAccountByClientSpecific.build())
                .build();
        session.execute(batchStatement);
    }

    public long countActiveByClientId(UUID clientId) {
        Select selectBankAccount = QueryBuilder
                .selectFrom(BankAccountIds.BANK_ACCOUNTS_BY_CLIENTS)
                .all()
                .where(Relation.column(BankAccountIds.CLIENT_ID).isEqualTo(literal(clientId)))
                .countAll();
        ResultSet resultSet = session.execute(selectBankAccount.build());
        return resultSet.one().getLong(0);
    }

    public void close() {
        session.close();
    }
}
