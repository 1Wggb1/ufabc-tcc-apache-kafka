# Experimentos Buffer e Modelo Pub/Sub

A suíte de testes está dividida em dois cenários principais:

* **`apache-kafka/`**: Experimentos utilizando o Apache Kafka como buffer de mensagens.
* **`without-apache-kafka/`**: Experimentos executados sem a camada do Apache Kafka (comunicação direta).

## Fluxo de Execução dos Experimentos

O script de automação principal (`runExperiment.sh`) realiza as seguintes etapas em sequência:

1. **Criação dos Containers:** Inicializa a infraestrutura e os containers das aplicações via `docker compose`.
2. **Estabilização:** Aguarda um tempo determinado para garantir a subida e estabilização completa dos serviços.
3. **Pausa para Monitoramento:** Interrompe a execução temporariamente para que o usuário inicie manualmente a coleta de métricas em outro terminal.
4. **Execução dos Testes:** Executa a carga de testes dos experimentos por meio de um container dedicado do **k6**.
5. **Finalização:** Aguarda um breve momento de arrefecimento antes de concluir e encerrar a rotina.

## Guia Passo a Passo

### 1. Iniciar o Monitoramento de Métricas (Obrigatório)

Antes ou durante a pausa inicial do experimento, abra um **terminal separado**, conceda permissão (se necessário) e execute o script de monitoramento do Docker:

```bash
chmod +x collectDockerStats.sh
./collectDockerStats.sh
```

> **Nota:** Este script deve ser mantido rodando em segundo plano/outro terminal durante toda a bateria de testes. Ele gera/atualiza continuamente o arquivo **`docker_monitoring.json`**.

### 2. Executar o Experimento

No terminal principal, navegue até a pasta do cenário desejado (`apache-kafka` ou `without-apache-kafka`) e execute:

```bash
chmod +x runExperiment.sh
./runExperiment.sh
```

Siga as instruções exibidas na tela para confirmar que a coleta de métricas via `collectDockerStats.sh` já foi iniciada antes de prosseguir com a fase do k6.

### 3. Encerramento

Assim que o script `runExperiment.sh` concluir a execução por completo:

* Retorne ao terminal onde o `./collectDockerStats.sh` está em execução.
* **Interrompa manualmente o script `collectDockerStats.sh`** (pressionando `Ctrl + C`) para finalizar a coleta e consolidar o arquivo `docker_monitoring.json`.