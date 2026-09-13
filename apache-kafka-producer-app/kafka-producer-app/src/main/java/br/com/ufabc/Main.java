package br.com.ufabc;


import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
class Main {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class)
                .run();
    }

    @RestController
    @RequestMapping("/produces")
    public static class ProducesController {
        private final String bootstrapServers;
        private final String topic;
        private final AtomicInteger requestNumber;

        public ProducesController(@Value(value = "${kafka.bootstrap.servers}") String bootstrapServers,
                                  @Value(value = "${kafka.topic.name}") String topic) {
            this.bootstrapServers = bootstrapServers;
            this.topic = topic;
            this.requestNumber = new AtomicInteger();
        }

        @PostMapping
        public ResponseEntity<String> print(){
            Properties properties = new Properties();
            properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
            String payload = """
                    {
                        "sku": "%s"
                    }
                    """.formatted(requestNumber.addAndGet(1));
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, "" + requestNumber.get(), payload);
            doSend(producer, producerRecord);
            producer.flush();
            producer.close();
            return ResponseEntity.ok("produces");
        }

        private static void doSend(KafkaProducer<String, String> producer, ProducerRecord<String, String> producerRecord) {
            producer.send(producerRecord, (recordMetadata, e) -> {
                if (e == null) {
                    IO.println("Received new metadata. \n" +
                            "Topic:" + recordMetadata.topic() + "\n" +
                            "Partition: " + recordMetadata.partition() + "\n" +
                            "Offset: " + recordMetadata.offset() + "\n" +
                            "Timestamp: " + recordMetadata.timestamp());
                    return;
                }
                IO.println("Error while producing %s".formatted(e.getMessage()));
            });
        }
    }
}