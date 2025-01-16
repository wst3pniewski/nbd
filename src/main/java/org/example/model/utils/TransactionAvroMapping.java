package org.example.model.utils;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import org.example.model.kafka.TransactionKafka;

import java.io.FileWriter;

public class TransactionAvroMapping {
    public void createTransactionSchema() {
        AvroMapper avroMapper = new AvroMapper();
        AvroSchema avroSchema = null;
        try {
            avroSchema = avroMapper.schemaFor(TransactionKafka.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }
        if (avroSchema == null) {
            return;
        }
        try (FileWriter fileWriter = new FileWriter("TransactionKafka.avsc")) {
            fileWriter.write(avroSchema.getAvroSchema().toString());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
