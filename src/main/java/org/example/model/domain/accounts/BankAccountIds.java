package org.example.model.domain.accounts;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class BankAccountIds {
    // TABLE / KEYSPACE
    public static final CqlIdentifier BANK_ACCOUNTS_BY_CLIENTS = CqlIdentifier.fromCql("bank_accounts_by_clients");
    public static final CqlIdentifier BANK_ACCOUNTS_KEYSPACE = CqlIdentifier.fromCql("bank_accounts");
    public static final CqlIdentifier BANK_ACCOUNT_TABLE = CqlIdentifier.fromCql("bank_account");

    // BANK ACCOUNT
    public static final CqlIdentifier ACCOUNT_ID = CqlIdentifier.fromCql("account_id");
    public static final CqlIdentifier BALANCE = CqlIdentifier.fromCql("balance");
    public static final CqlIdentifier CLIENT_ID = CqlIdentifier.fromCql("client_id");
    public static final CqlIdentifier IS_ACTIVE = CqlIdentifier.fromCql("is_active");
    public static final CqlIdentifier CREATION_DATE = CqlIdentifier.fromCql("creation_date");
    public static final CqlIdentifier CLOSE_DATE = CqlIdentifier.fromCql("close_date");
    public static final CqlIdentifier DISCRIMINATOR = CqlIdentifier.fromCql("discriminator");

    // SAVING ACCOUNT
    public static final CqlIdentifier INTEREST_RATE = CqlIdentifier.fromCql("interest_rate");
    // JUNIOR ACCOUNT
    public static final CqlIdentifier PARENT_ID = CqlIdentifier.fromCql("parent_id");
    // STANDARD ACCOUNT
    public static final CqlIdentifier DEBIT_LIMIT = CqlIdentifier.fromCql("debit_limit");
    public static final CqlIdentifier DEBIT = CqlIdentifier.fromCql("debit");
}
