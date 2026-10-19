package br.com.ufabc;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

class Main {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final KafkaConsumer<String, String> CONSUMER = getConfiguredConsumer();

    public static void main(String[] args) throws InterruptedException {
        consumeMessages();
    }

    public record Event(String sku){
    }

    private static void consumeMessages() throws InterruptedException {
        String topic = System.getenv("TOPIC_NAME");
        CONSUMER.subscribe(List.of(topic));
        int pollFrequencyMillis = Integer.parseInt(System.getenv("POLL_FREQUENCY_MILLIS"));
        int fakeProcessingTimeMillisOrDefault2Seconds = Integer.parseInt(Optional.ofNullable(System.getenv("FAKE_PROCESSING_TIME_MILLIS"))
                .orElse("2000"));
        while(true){
            ConsumerRecords<String, String> records = CONSUMER.poll(Duration.ofMillis(pollFrequencyMillis));
            for (ConsumerRecord<String, String> record : records){
                IO.println("Key: " + record.key() + ", Partition: " + record.partition() + ", Offset:" + record.offset());
                Event event = OBJECT_MAPPER.readValue(record.value(), Event.class);
                IO.println("[%s] Starting event %s consume".formatted(LocalDateTime.now(),event));
                Thread.sleep(fakeProcessingTimeMillisOrDefault2Seconds);
                IO.println("[%s] Event %s processed".formatted(LocalDateTime.now(), event));
            }
        }
    }

    private static KafkaConsumer<String, String> getConfiguredConsumer() {
        Properties properties = new Properties();
        String bootstrapServers = System.getenv("BOOTSTRAP_SERVERS");
        System.out.println(bootstrapServers);
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        String groupId = System.getenv("GROUP_ID");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        String offsetResetConfig = System.getenv("OFFSET_RESET_CONFIG");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetResetConfig);
        String maxPollRecordsConfig = System.getenv("MAX_POLL_RECORDS");
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecordsConfig);
        return new KafkaConsumer<>(properties);
    }
}