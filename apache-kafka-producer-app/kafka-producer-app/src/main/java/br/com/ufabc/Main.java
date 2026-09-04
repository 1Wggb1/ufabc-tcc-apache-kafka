package br.com.ufabc;


import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@SpringBootApplication
class Main {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class)
                .run();
    }

    @RestController
    @RequestMapping("/produces")
    public static class Controller {
        @GetMapping
        public ResponseEntity<String> print(){
            String bootstrapServers = "127.0.0.1:9092";

            // create Producer properties
            Properties properties = new Properties();
            properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

            for(int i = 0; i < 100; i++){
                ProducerRecord<String, String> producerRecord =
                        new ProducerRecord<>("first_topic", "" + i,"""
                            {
                                "sku": "1020rit"
                            }
                            """);
                producer.send(producerRecord, (recordMetadata, e) -> {
                    // executes every time a record is successfully sent or an exception is thrown
                    if (e == null) {
                        // the record was successfully sent
                        IO.println("Received new metadata. \n" +
                                "Topic:" + recordMetadata.topic() + "\n" +
                                "Partition: " + recordMetadata.partition() + "\n" +
                                "Offset: " + recordMetadata.offset() + "\n" +
                                "Timestamp: " + recordMetadata.timestamp());
                    } else {
                        IO.println("Error while producing %s".formatted(e.getMessage()));
                    }
                });
            }
            producer.flush();
            producer.close();

            return ResponseEntity.ok("produces");
        }
    }
}