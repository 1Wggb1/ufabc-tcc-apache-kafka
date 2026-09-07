package br.com.ufabc;


import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
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
        private final String bootstrapServers;
        private final String topic;

        public Controller(@Value(value = "${kafka.bootstrap.servers}") String bootstrapServers,
                          @Value(value = "${kafka.topic.name}") String topic) {
            this.bootstrapServers = bootstrapServers;
            this.topic = topic;
        }

        @GetMapping
        public ResponseEntity<String> print(){

            Properties properties = new Properties();
            System.out.println(bootstrapServers);
            properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

            for(int i = 0; i < 100; i++){
                ProducerRecord<String, String> producerRecord =
                        new ProducerRecord<>(topic, "" + i,"""
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