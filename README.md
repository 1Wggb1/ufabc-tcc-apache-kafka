# ufabc-tcc-apache-kafka

> Folders:
> - latex-template (template do artigo científico)
> - apache-kafka-consumer-app (aplicação consumidora de eventos do Apache Kafka)
> - apache-kafka-producer-app (aplicação produtora de eventos do Apache Kafka)
> - consumer-app (aplicação consumidora)
> - producer-app (aplicação produtora)

[Producer com java](https://www.conduktor.io/kafka/complete-kafka-producer-with-java)

[Consumer com java](https://www.conduktor.io/kafka/complete-kafka-consumer-with-java)

## Rodando exemplos com docker compose
Apache Kafka como Backpressure (Apache Kafka, Consumidor e Produtor):
```
./apache-kafka-consumer-app/kafka-consumer-app/gradlew clean build && ./apache-kafka-producer-app/kafka-producer-app/gradlew clean build && docker compose up --build -d
```

Aplicação sem Apache Kafka (Consumidor e Produtor):
```
./consumer-app/rest-consumer/gradlew clean build && ./producer-app/rest-producer/gradlew clean build && docker compose up --build -d
```

## Rodando cenário de carga
### Utilizando Apache Kafka
```
docker run --network=ufabc-tcc-apache-kafka_kafka-network --rm -i grafana/k6 run - <k6-load-test-apache-kafka-producer.js
```

### Sem Apache Kafka
```
docker run --network=ufabc-tcc-apache-kafka_rest-network --rm -i grafana/k6 run - <k6-load-test-rest-producer.js
```