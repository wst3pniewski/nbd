package org.example.model.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionAvroMappingTest {

    @Test
    void toAvro() {
        TransactionAvroMapping transactionAvroMapping = new TransactionAvroMapping();
        transactionAvroMapping.createTransactionSchema();
    }
}