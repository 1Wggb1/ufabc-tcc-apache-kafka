# Experimentos de Avaliação de Backpressure

Este repositório possibilita a reprodutibilidade dos experimentos do artigo científico `Mecanismos de Backpressure com Apache Kakfa`.
Os experimentos buscam analisar os mecanismos de Backpressure de desacoplamento, controle de vazão e escalabilidade em arquiteturas orientadas a eventos utilizando o **Apache Kafka**.

## Escopo dos Experimentos

Os testes estão divididos em três experimentos:

### 1. Buffer e Modelo Pub/Sub

- **Diretório:** `./experiments/buffer-pubsub-backpressure-comparison`
- **O que avalia:** O nível de desacoplamento temporal e estrutural proporcionado pelo uso de um buffer intermediário e Pub-Sub (obtido pelo Apache Kafka) em comparação à comunicação direta entre consumidores e produtores (HTTP/REST). O experimento analisa como o buffer retém a carga e previne a sobrecarga dos serviços consumidores, evitando a perda de eventos durante picos de requisições e garantindo a resiliência via backpressure.

### 2. Quotas de Rede (*Network Quotas*)

- **Diretório:** `./experiments/quotas-backpressure-comparison`
- **O que avalia:** Como a aplicação de *backpressure* no lado do produtor (via limitação de taxa de transferência/bytes) reduz ativamente a vazão de geração e envio de eventos para o Apache Kafka. A análise foca em demonstrar como esse estrangulamento controlado compatibiliza a taxa de produção com a capacidade de processamento do consumidor, evitando o  crescimento descontrolado de eventos nos tópicos.

### 3. Consumer Groups

- **Diretório:** `./experiments/consumer-group-backpressure-comparison`
- **O que avalia:** A eficácia do escalamento horizontal de consumidores via Apache Kafka (backpressure passivo). O experimento examina como a adição gradativa de membros a um *Consumer Group* aumenta a taxa global de ingestão e processamento, reequilibrando o consumo em relação à taxa de produção e reduzindo o tempo de retenção das eventos na fila (consumer lag).

> **OBS: a para a reprodução dos experimentos existe um README.md explicando sobre a execução e os passos dos experimentos nos diretório citados.**