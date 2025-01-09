package org.example.model.domain;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class TransactionIds {
    // TABLE / KEYSPACE
    public static final CqlIdentifier BANK_ACCOUNTS_KEYSPACE = CqlIdentifier.fromCql("bank_accounts");
    public static final CqlIdentifier TRANSACTIONS_BY_SOURCE_ACCOUNT = CqlIdentifier.fromCql("transactions_by_source_account");
    public static final CqlIdentifier TRANSACTIONS_BY_DESTINATION_ACCOUNT = CqlIdentifier.fromCql("transactions_by_destination_account");
    public static final CqlIdentifier TRANSACTIONS = CqlIdentifier.fromCql("transactions");

    // TRANSACTION
    public static final CqlIdentifier TRANSACTION_ID = CqlIdentifier.fromCql("transaction_id");
    public static final CqlIdentifier SOURCE_ACCOUNT = CqlIdentifier.fromCql("source_account");
    public static final CqlIdentifier DESTINATION_ACCOUNT = CqlIdentifier.fromCql("destination_account");
    public static final CqlIdentifier AMOUNT = CqlIdentifier.fromCql("amount");
}
