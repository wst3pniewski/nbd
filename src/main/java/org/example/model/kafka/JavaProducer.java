package org.example.model.kafka;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import kafka.TransactionKafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.example.model.Transaction;

import java.util.Properties;

public class JavaProducer implements AutoCloseable {
    private KafkaProducer<String, TransactionKafka> producer;
    private final String TOPIC = "transactions";

    public JavaProducer() {
        initProducer();
        producer.initTransactions();
    }

    private void initProducer() {
        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "MongoRedisKafka");
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192, kafka2:9292, kafka3:9392");
        // semantyka dokladnie raz
        producerConfig.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        producerConfig.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "MongoRedisKafka-1");
        producerConfig.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        producer = new KafkaProducer<>(producerConfig);
    }

    public void sendTransactionToKafka(Transaction transaction) {
        TransactionKafka transactionKafka = TransactionKafka.newBuilder()
                .setId(transaction.getId().toString())
                .setAmount(transaction.getAmount())
                .setSourceAccount(transaction.getSourceAccount().toString())
                .setDestinationAccount(transaction.getDestinationAccount().toString())
                .build();
        ProducerRecord<String, TransactionKafka> producerRecord = new ProducerRecord<>(TOPIC, transactionKafka.getId().toString(), transactionKafka);

        try {
            producer.beginTransaction();
            producer.send(producerRecord);
            producer.commitTransaction();
        } catch (ProducerFencedException e) {
            producer.close();
        } catch (KafkaException e) {
            producer.abortTransaction();
        }
    }

    @Override
    public void close() throws Exception {
        producer.close();
    }
}
