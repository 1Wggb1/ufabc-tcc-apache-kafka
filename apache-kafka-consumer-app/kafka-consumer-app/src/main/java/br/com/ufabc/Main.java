package br.com.ufabc;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

class Main {
    public static void main(String[] args) {
        consumeMessages();
    }

    private static void consumeMessages() {
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

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        String topic = System.getenv("TOPIC_NAME");
        consumer.subscribe(List.of(topic));


        int pollFrequencyMillis = Integer.parseInt(System.getenv("POLL_FREQUENCY_MILLIS"));
        while(true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(pollFrequencyMillis));
            for (ConsumerRecord<String, String> record : records){
                IO.println("Key: " + record.key() + ", Value: " + record.value());
                IO.println("Partition: " + record.partition() + ", Offset:" + record.offset());
            }
        }
    }
}