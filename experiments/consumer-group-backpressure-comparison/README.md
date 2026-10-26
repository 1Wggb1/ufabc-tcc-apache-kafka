# Experimentos Consumer Group

Esta suíte de testes avalia o comportamento da aplicação consumidora para avaliar a capacidade de vazão pela variação do volume de eventos e o número de instâncias receptoras (membros) no *Consumer Group*.

## Configuração dos Cenários e Parâmetros

Antes de iniciar a execução, ajuste manualmente os parâmetros de carga e a escala dos consumidores conforme a matriz abaixo:

### Matriz de Parâmetros (k6)

| Cenário | Usuários Virtuais (`vus`) | Iterações Totais (`iterations`) |
| :--- | :---: | :---: |
| **250 Eventos** | `125` | `250` |
| **500 Eventos** | `250` | `500` |
| **750 Eventos** | `375` | `750` |
| **1000 Eventos** | `500` | `1000` |

> **OBS:**
> - **Carga e VUs:** Configure as propriedades no arquivo `k6-load-test-consumer-groups-producer.js`.
> - **Quantidade de Consumidores:** Por padrão, a infraestrutura está configurada para executar com **3 consumidores**. Caso queira reduzir a quantidade de instâncias ativas, abra o arquivo `docker-compose.yaml` da pasta do cenário atual e comente as seções/blocos referentes aos containers sobressalentes.

## Fluxo de Execução dos Experimentos

O script de orquestração `runExperimentConsumerGroups.sh` executa o ciclo de vida do teste na seguinte sequência:

1. **Criação dos Containers:** Inicializa a infraestrutura e os containers das aplicações via `docker compose`.
2. **Estabilização:** Aguarda um tempo determinado para garantir a subida e estabilização completa dos serviços.
3. **Pausa para Monitoramento:** Interrompe a execução temporariamente para que o usuário inicie manualmente a coleta de métricas em outro terminal.
4. **Execução dos Testes:** Executa a carga de testes dos experimentos por meio de um container dedicado do **k6**.
5. **Finalização:** Aguarda um breve momento de arrefecimento antes de concluir e encerrar a rotina.

## Guia Passo a Passo

### 1. Iniciar o Monitoramento de Métricas (Opcional)

Antes de autorizar o prosseguimento do script principal, abra uma janela de **terminal separada** e inicie o monitoramento do Docker:

```bash
chmod +x collectDockerStats.sh
./collectDockerStats.sh
```
> **Nota:** Este script deve ser mantido rodando em segundo plano/outro terminal durante toda a bateria de testes. Ele gera/atualiza continuamente o arquivo **`docker_monitoring.json`**.

### 2. Executar o Experimento

No terminal principal, navegue até a pasta do cenário desejado e execute:

```bash
chmod +x runExperimentConsumerGroups.sh
./runExperimentConsumerGroups.sh
```

Siga as instruções exibidas na tela para confirmar que a coleta de métricas via `collectDockerStats.sh` já foi iniciada antes de prosseguir com a fase do k6.

### 3. Encerramento

Assim que o script `runExperimentConsumerGroups.sh` concluir a execução por completo:

* Retorne ao terminal onde o `./collectDockerStats.sh` está em execução.
* **Interrompa manualmente o script `collectDockerStats.sh`** (pressionando `Ctrl + C`) para finalizar a coleta e consolidar o arquivo `docker_monitoring.json`.