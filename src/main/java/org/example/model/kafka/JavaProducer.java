package org.example.model.kafka;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import kafka.TransactionKafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.model.Transaction;

import java.util.Properties;

public class JavaProducer implements AutoCloseable {
    private KafkaProducer<String, TransactionKafka> producer;
    private final String TOPIC = "transactions";

    public JavaProducer() {
        initProducer();
    }

    private void initProducer() {
        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "local");
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192, kafka2:9292, kafka3:9392");
        producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
        // semantyka dokladnie raz
        producerConfig.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        producerConfig.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        producer = new KafkaProducer<>(producerConfig);
    }

    public void sendTransactionToKafka(Transaction transaction) {
        TransactionKafka transactionKafka = TransactionKafka.newBuilder().setId(transaction.getId().toString())
                .setAmount(transaction.getAmount())
                .setSourceAccount(transaction.getSourceAccount().toString())
                .setDestinationAccount(transaction.getDestinationAccount().toString())
                .build();
        ProducerRecord<String, TransactionKafka> producerRecord = new ProducerRecord<>(TOPIC, transactionKafka.getId().toString(), transactionKafka);

        producer.send(producerRecord, (metadata, exception) -> {
            if (exception == null) {
                System.out.println("Message produced, record metadata: " + metadata);
                System.out.println("Producing message with data: " + producerRecord.value());
            } else {
                System.err.println("Error producing message: " + exception.getMessage());
            }
        });

        producer.flush();
    }

    @Override
    public void close() throws Exception {
        producer.close();
    }
}
