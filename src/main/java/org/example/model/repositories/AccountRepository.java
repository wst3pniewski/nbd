package org.example.model.repositories;


import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import org.example.model.dao.JuniorAccountDao;
import org.example.model.dao.SavingAccountDao;
import org.example.model.dao.StandardAccountDao;
import org.example.model.domain.accounts.BankAccount;
import org.example.model.domain.accounts.JuniorAccount;
import org.example.model.domain.accounts.SavingAccount;
import org.example.model.domain.accounts.StandardAccount;
import org.example.model.mappers.BankAccountMapper;
import org.example.model.mappers.BankAccountMapperBuilder;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;


public class AccountRepository implements Repository<BankAccount> {

    private static CqlSession session;
    private static BankAccountMapper accountMapper;
    private static JuniorAccountDao juniorAccountDao;
    private static SavingAccountDao savingAccountDao;
    private static StandardAccountDao standardAccountDao;

    public void initSession() {
        session = CqlSession.builder()
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

        SimpleStatement createJuniorAccounts =
                SchemaBuilder.createTable(CqlIdentifier.fromCql("bank_accounts"), CqlIdentifier.fromCql("junior_accounts"))
                        .ifNotExists()
                        .withPartitionKey(CqlIdentifier.fromCql("account_id"), DataTypes.UUID)
                        .withColumn(CqlIdentifier.fromCql("balance"), DataTypes.DECIMAL)
                        .withColumn(CqlIdentifier.fromCql("client_id"), DataTypes.UUID)
                        .withColumn(CqlIdentifier.fromCql("is_active"), DataTypes.BOOLEAN)
                        .withColumn(CqlIdentifier.fromCql("creation_date"), DataTypes.DATE)
                        .withColumn(CqlIdentifier.fromCql("close_date"), DataTypes.DATE)
                        .withColumn(CqlIdentifier.fromCql("parent_id"), DataTypes.UUID)
                        .build();
        session.execute(createJuniorAccounts);

        SimpleStatement createSavingAccounts =
                SchemaBuilder.createTable(CqlIdentifier.fromCql("bank_accounts"), CqlIdentifier.fromCql("saving_accounts"))
                        .ifNotExists()
                        .withPartitionKey(CqlIdentifier.fromCql("account_id"), DataTypes.UUID)
                        .withColumn(CqlIdentifier.fromCql("balance"), DataTypes.DECIMAL)
                        .withColumn(CqlIdentifier.fromCql("client_id"), DataTypes.UUID)
                        .withColumn(CqlIdentifier.fromCql("is_active"), DataTypes.BOOLEAN)
                        .withColumn(CqlIdentifier.fromCql("creation_date"), DataTypes.DATE)
                        .withColumn(CqlIdentifier.fromCql("close_date"), DataTypes.DATE)
                        .withColumn(CqlIdentifier.fromCql("interest_rate"), DataTypes.DECIMAL)
                        .build();
        session.execute(createSavingAccounts);

        SimpleStatement createStandardAccounts =
                SchemaBuilder.createTable(CqlIdentifier.fromCql("bank_accounts"), CqlIdentifier.fromCql("standard_accounts"))
                        .ifNotExists()
                        .withPartitionKey(CqlIdentifier.fromCql("account_id"), DataTypes.UUID)
                        .withColumn(CqlIdentifier.fromCql("balance"), DataTypes.DECIMAL)
                        .withColumn(CqlIdentifier.fromCql("client_id"), DataTypes.UUID)
                        .withColumn(CqlIdentifier.fromCql("is_active"), DataTypes.BOOLEAN)
                        .withColumn(CqlIdentifier.fromCql("creation_date"), DataTypes.DATE)
                        .withColumn(CqlIdentifier.fromCql("close_date"), DataTypes.DATE)
                        .withColumn(CqlIdentifier.fromCql("debit_limit"), DataTypes.DECIMAL)
                        .withColumn(CqlIdentifier.fromCql("debit"), DataTypes.DECIMAL)
                        .build();
        session.execute(createStandardAccounts);

        SimpleStatement createStandardAccountsByClients =
                SchemaBuilder.createTable(CqlIdentifier.fromCql("bank_accounts"), CqlIdentifier.fromCql("standard_accounts_by_clients"))
                        .ifNotExists()
                        .withPartitionKey(CqlIdentifier.fromCql("client_id"), DataTypes.UUID)
                        .withClusteringColumn(CqlIdentifier.fromCql("account_id"), DataTypes.UUID)
                        .withColumn(CqlIdentifier.fromCql("balance"), DataTypes.DECIMAL)
                        .withColumn(CqlIdentifier.fromCql("is_active"), DataTypes.BOOLEAN)
                        .withColumn(CqlIdentifier.fromCql("creation_date"), DataTypes.DATE)
                        .withColumn(CqlIdentifier.fromCql("close_date"), DataTypes.DATE)
                        .withColumn(CqlIdentifier.fromCql("debit_limit"), DataTypes.DECIMAL)
                        .withColumn(CqlIdentifier.fromCql("debit"), DataTypes.DECIMAL)
                        .build();
        session.execute(createStandardAccountsByClients);
    }

    public AccountRepository() {
        initSession();
        accountMapper = new BankAccountMapperBuilder(session).build();
        juniorAccountDao = accountMapper.juniorAccountDao(CqlIdentifier.fromCql("bank_accounts"));
        savingAccountDao = accountMapper.savingAccountDao(CqlIdentifier.fromCql("bank_accounts"));
        standardAccountDao = accountMapper.standardAccountDao(CqlIdentifier.fromCql("bank_accounts"));

    }

    public void add(BankAccount account) {
        if (account == null) {
            return;
        }

        switch (account.getClass().getSimpleName()) {
            case "JuniorAccount" -> juniorAccountDao.create((JuniorAccount) account);
            case "SavingAccount" -> savingAccountDao.create((SavingAccount) account);
            case "StandardAccount" -> standardAccountDao.create((StandardAccount) account);
            default ->
                    throw new IllegalArgumentException("Unsupported BankAccount type: " + account.getClass().getName());
        }
        ;
    }

    public List<BankAccount> findAll() {
        List<BankAccount> accounts = new ArrayList<>();
        accounts.addAll(juniorAccountDao.findAll().all());
        accounts.addAll(savingAccountDao.findAll().all());
        accounts.addAll(standardAccountDao.findAll().all());
        return accounts;
    }


    public BankAccount findById(UUID id) {
        BankAccount account;
        account = juniorAccountDao.findById(id);
        if (account != null) {
            return account;
        }
        account = savingAccountDao.findById(id);
        if (account != null) {
            return account;
        }
        return standardAccountDao.findById(id);
    }

    public void update(BankAccount account) {
//        if (account == null) {
//            return null;
//        }
//        BankAccountDTO accountDTO = BankAccountDTOMapper.toDTO(account);
//        Bson filter = Filters.eq("_id", account.getId());
//        Bson setUpdate = null;
//        if (accountDTO instanceof StandardAccountDTO) {
//            setUpdate = Updates.combine(
//                    Updates.set("balance", account.getBalance()),
//                    Updates.set("active", account.getActive()),
//                    Updates.set("closeDate", account.getCloseDate()),
//                    Updates.set("debit", ((StandardAccount) account).getDebit()),
//                    Updates.set("debitLimit", ((StandardAccount) account).getDebitLimit())
//            );
//        } else if (accountDTO instanceof SavingAccountDTO) {
//            setUpdate = Updates.combine(
//                    Updates.set("balance", account.getBalance()),
//                    Updates.set("active", account.getActive()),
//                    Updates.set("closeDate", account.getCloseDate()),
//                    Updates.set("interestRate", ((SavingAccount) account).getInterestRate())
//            );
//        } else if (accountDTO instanceof JuniorAccountDTO) {
//            setUpdate = Updates.combine(
//                    Updates.set("balance", account.getBalance()),
//                    Updates.set("active", account.getActive()),
//                    Updates.set("closeDate", account.getCloseDate())
//            );
//        }
//        if (setUpdate == null) {
//            return null;
//        }
//        bankAccounts.updateOne(filter, setUpdate);
//        return account;

        return;
    }

    public List<BankAccount> getAccountsByClientId(UUID clientId) {
        Select selectFromWhere = QueryBuilder.selectFrom("bank_accounts", "standard_accounts_by_clients")
                .all()
                .whereColumn("client_id").isEqualTo(QueryBuilder.literal(clientId));
        ResultSet resultSet = session.execute(selectFromWhere.build());
        List<BankAccount> accounts = new ArrayList<>();
        List<Row> result = resultSet.all();
    }


    public long countActiveByClientId(UUID clientId) {
//        Bson filter = Filters.and(
//                Filters.eq("client._id", clientId),
//                Filters.eq("active", true)
//        );
//        return bankAccounts.countDocuments(filter);

        return 1l;
    }

//    public boolean updateClient(Client client) {

    /// /        Bson filter = Filters.eq("client._id", client.getId());
    /// /        Bson setUpdate = Updates.set("client", client);
    /// /        UpdateResult updateResult = bankAccounts.updateMany(filter, setUpdate);
    /// /        return updateResult.getModifiedCount() != 0;
//
//        return false;
//    }
    public void close() {
        session.close();
    }
}
