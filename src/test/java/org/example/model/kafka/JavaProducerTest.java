package org.example.model.kafka;

import org.example.model.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

class JavaProducerTest {
    private static JavaProducer producer;

    @BeforeAll
    static void beforeAll() {
        producer = new JavaProducer();
    }

    @AfterAll
    static void afterAll() {
        try {
            producer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void sendTransactionToKafka(){
        for (int i = 0; i < 100; i++) {
            Transaction transaction = new Transaction(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.valueOf(2000));
            producer.sendTransactionToKafka(transaction);
        }
    }

}