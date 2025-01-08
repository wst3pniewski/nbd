package org.example.model;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import org.example.model.domain.accounts.*;

import java.util.List;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class BankAccountQueryProvider {
    private final CqlSession session;
    private EntityHelper<JuniorAccount> juniorAccountEntityHelper;
    private EntityHelper<SavingAccount> savingAccountEntityHelper;
    private EntityHelper<StandardAccount> standardAccountEntityHelper;

    public BankAccountQueryProvider(MapperContext ctx, EntityHelper<JuniorAccount> juniorAccountEntityHelper, EntityHelper<SavingAccount> savingAccountEntityHelper, EntityHelper<StandardAccount> standardAccountEntityHelper) {
        session = ctx.getSession();
        this.juniorAccountEntityHelper = juniorAccountEntityHelper;
        this.savingAccountEntityHelper = savingAccountEntityHelper;
        this.standardAccountEntityHelper = standardAccountEntityHelper;
    }

    public void create(BankAccount bankAccount) {
        session.execute(
                switch (bankAccount.getDiscriminator()) {
                    case "JUNIOR" -> {
                        JuniorAccount juniorAccount = (JuniorAccount) bankAccount;
                        yield session.prepare(juniorAccountEntityHelper.insert().build())
                                .bind()
                                .setUuid(BankAccountIds.PARENT_ID, juniorAccount.getParentId())
                                .setUuid(BankAccountIds.ACCOUNT_ID, juniorAccount.getId())
                                .setUuid(BankAccountIds.CLIENT_ID, juniorAccount.getClientId())
                                .setLocalDate(BankAccountIds.CREATION_DATE, juniorAccount.getCreationDate())
                                .setBigDecimal(BankAccountIds.BALANCE, juniorAccount.getBalance())
                                .setBoolean(BankAccountIds.IS_ACTIVE, juniorAccount.getActive())
                                .setLocalDate(BankAccountIds.CLOSE_DATE, juniorAccount.getCloseDate())
                                .setString(BankAccountIds.DISCRIMINATOR, juniorAccount.getDiscriminator());
                    }
                    case "SAVING" -> {
                        SavingAccount savingAccount = (SavingAccount) bankAccount;
                        yield session.prepare(savingAccountEntityHelper.insert().build())
                                .bind()
                                .setBigDecimal(BankAccountIds.INTEREST_RATE, savingAccount.getInterestRate())
                                .setUuid(BankAccountIds.ACCOUNT_ID, savingAccount.getId())
                                .setUuid(BankAccountIds.CLIENT_ID, savingAccount.getClientId())
                                .setLocalDate(BankAccountIds.CREATION_DATE, savingAccount.getCreationDate())
                                .setBigDecimal(BankAccountIds.BALANCE, savingAccount.getBalance())
                                .setBoolean(BankAccountIds.IS_ACTIVE, savingAccount.getActive())
                                .setLocalDate(BankAccountIds.CLOSE_DATE, savingAccount.getCloseDate())
                                .setString(BankAccountIds.DISCRIMINATOR, savingAccount.getDiscriminator());
                    }
                    case "STANDARD" -> {
                        StandardAccount standardAccount = (StandardAccount) bankAccount;
                        yield session.prepare(standardAccountEntityHelper.insert().build())
                                .bind()
                                .setBigDecimal(BankAccountIds.DEBIT, standardAccount.getDebit())
                                .setBigDecimal(BankAccountIds.DEBIT_LIMIT, standardAccount.getDebitLimit())
                                .setUuid(BankAccountIds.ACCOUNT_ID, standardAccount.getId())
                                .setUuid(BankAccountIds.CLIENT_ID, standardAccount.getClientId())
                                .setLocalDate(BankAccountIds.CREATION_DATE, standardAccount.getCreationDate())
                                .setBigDecimal(BankAccountIds.BALANCE, standardAccount.getBalance())
                                .setBoolean(BankAccountIds.IS_ACTIVE, standardAccount.getActive())
                                .setLocalDate(BankAccountIds.CLOSE_DATE, standardAccount.getCloseDate())
                                .setString(BankAccountIds.DISCRIMINATOR, standardAccount.getDiscriminator());
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + bankAccount.getDiscriminator());
                }
        );
    }

    public BankAccount findById(UUID bankAccountId) {
        Select selectBankAccount = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("bank_account"))
                .all()
                .where(Relation.column(BankAccountIds.ACCOUNT_ID).isEqualTo(literal(bankAccountId)));
        Row row = session.execute(selectBankAccount.build()).one();
        if (row == null) {
            return null;
        }
        String discriminator = row.getString(BankAccountIds.DISCRIMINATOR);
        return switch (discriminator) {
            case "JUNIOR" -> getJuniorAccount(row);
            case "SAVING" -> getSavingAccount(row);
            case "STANDARD" -> getStandardAccount(row);
            default -> throw new IllegalStateException("Unexpected value: " + discriminator);
        };
    }

    public List<BankAccount> findAll(){
        Select selectBankAccount = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("bank_account"))
                .all();
        ResultSet resultSet = session.execute(selectBankAccount.build());
        List<Row> result = resultSet.all();
        List<BankAccount> bankAccounts = result.stream().map(row -> {
            String discriminator = row.getString(BankAccountIds.DISCRIMINATOR);
            return switch (discriminator) {
                case "JUNIOR" -> getJuniorAccount(row);
                case "SAVING" -> getSavingAccount(row);
                case "STANDARD" -> getStandardAccount(row);
                default -> throw new IllegalStateException("Unexpected value: " + discriminator);
            };
        }).toList();
        return bankAccounts;
    }

    public static JuniorAccount getJuniorAccount(Row juniorAccount) {
        return new JuniorAccount(
                juniorAccount.getUuid(BankAccountIds.ACCOUNT_ID),
                juniorAccount.getUuid(BankAccountIds.CLIENT_ID),
                juniorAccount.getUuid(BankAccountIds.PARENT_ID),
                juniorAccount.getLocalDate(BankAccountIds.CREATION_DATE),
                juniorAccount.getBigDecimal(BankAccountIds.BALANCE),
                juniorAccount.getString(BankAccountIds.DISCRIMINATOR),
                juniorAccount.getBoolean(BankAccountIds.IS_ACTIVE),
                juniorAccount.getLocalDate(BankAccountIds.CLOSE_DATE)
        );

    }

    public static SavingAccount getSavingAccount(Row savingAccount) {
        return new SavingAccount(
                savingAccount.getUuid(BankAccountIds.ACCOUNT_ID),
                savingAccount.getBigDecimal(BankAccountIds.INTEREST_RATE),
                savingAccount.getUuid(BankAccountIds.CLIENT_ID),
                savingAccount.getLocalDate(BankAccountIds.CREATION_DATE),
                savingAccount.getBigDecimal(BankAccountIds.BALANCE),
                savingAccount.getString(BankAccountIds.DISCRIMINATOR),
                savingAccount.getBoolean(BankAccountIds.IS_ACTIVE),
                savingAccount.getLocalDate(BankAccountIds.CLOSE_DATE)
        );
    }

    public static StandardAccount getStandardAccount(Row standardAccount) {
        return new StandardAccount(
                standardAccount.getUuid(BankAccountIds.ACCOUNT_ID),
                standardAccount.getBigDecimal(BankAccountIds.DEBIT_LIMIT),
                standardAccount.getBigDecimal(BankAccountIds.DEBIT),
                standardAccount.getUuid(BankAccountIds.CLIENT_ID),
                standardAccount.getLocalDate(BankAccountIds.CREATION_DATE),
                standardAccount.getBigDecimal(BankAccountIds.BALANCE),
                standardAccount.getString(BankAccountIds.DISCRIMINATOR),
                standardAccount.getBoolean(BankAccountIds.IS_ACTIVE),
                standardAccount.getLocalDate(BankAccountIds.CLOSE_DATE)
        );
    }
}
