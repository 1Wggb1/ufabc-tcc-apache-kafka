package br.com.ufabc;


import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.HttpStatusCode;
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
        private final String topic;
        private final AtomicInteger requestNumber;
        private final KafkaProducer<String, String> producer;

        public ProducesController(@Value(value = "${kafka.bootstrap.servers}") String bootstrapServers,
                                  @Value(value = "${kafka.topic.name}") String topic) {
            this.topic = topic;
            this.requestNumber = new AtomicInteger();
            Properties properties = new Properties();
            properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            this.producer = new KafkaProducer<>(properties);
        }

        @PostMapping
        public ResponseEntity<String> producesEvents(){
            IO.println("Starting event producer. Generating event %s".formatted(requestNumber.addAndGet(1)));
            String payload = """
                    {
                        "sku": "%s"
                    }
                    """.formatted(requestNumber.addAndGet(1));
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, "" + requestNumber.get(), payload);
            try {
                doSend(producer, producerRecord);
                IO.println("Event %s produced successfully".formatted(payload));
            } catch (Exception e){
                IO.println("Exception %s in event %s".formatted(e.getMessage(), payload));
                return ResponseEntity.status(HttpStatusCode.valueOf(504)).build();
            } finally {
                producer.flush();
            }
            return ResponseEntity.ok("produced");
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
                throw new IllegalStateException(e);
            });
        }
    }
}