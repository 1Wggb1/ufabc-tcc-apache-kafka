# Experimentos Quotas de Rede

Esta suíte de testes avalia o comportamento da vazão da aplicação produtora através da variação das quotas de rede em bytes.

## Configuração dos Cenários e Mapeamento de Diretórios

O comportamento das configurações varia de acordo com o diretório do experimento executado:

* **Diretório `default/` (Quotas Standard):** Nenhuma modificação manual é necessária. O ambiente utiliza as configurações e limites padrão do Kafka broker.
* **Diretório `reduced/` (Quotas Reduzidas):** **Exige alteração manual** no script de inicialização para definir a taxa limite desejada antes de rodar os testes.

## Matriz de Configuração de Quotas (`reduced/`)

Para os testes na pasta `reduced/`, localize no script (`runExperimentReducedQuotas.sh`) a seguinte linha de comando e ajuste o parâmetro `'producer_byte_rate'` para o valor equivalente em bytes do cenário desejado:

```
...
echo "Running producer rate quotas configuration..."
docker exec -it broker /opt/kafka/bin/kafka-configs.sh --bootstrap-server localhost:9092 --alter --add-config 'producer_byte_rate=1024' --entity-type clients --entity-name ufabc.producer
...
```

### Valores por cenário

| Cenário | Quota (KB/s) | Valor do Parâmetro (`producer_byte_rate`) |
| :--- | :---: | :---: |
| **1 KB** | `1 KB/s` | `'producer_byte_rate=1024'` |
| **2 KB** | `2 KB/s` | `'producer_byte_rate=2048'` |
| **4 KB** | `4 KB/s` | `'producer_byte_rate=4096'` |
| **8 KB** | `8 KB/s` | `'producer_byte_rate=8192'` |
| **16 KB** | `16 KB/s` | `'producer_byte_rate=16384'` |

> **OBS: Certifique-se de salvar o script alterado com o valor exato em bytes correspondente à quota do teste antes de prosseguir com a execução.**

## Fluxo de Execução dos Experimentos

O script de orquestração `runExperimentReducedQuotas.sh` ou `runExperimentDefaultQuotas.sh` executa o ciclo de vida do teste na seguinte sequência:

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
chmod +x runExperimentReducedQuotas.sh
./runExperimentReducedQuotas.sh
```

ou

```bash
chmod +x runExperimentDefaultQuotas.sh
./runExperimentDefaultQuotas.sh
```

Siga as instruções exibidas na tela para confirmar que a coleta de métricas via `collectDockerStats.sh` já foi iniciada antes de prosseguir com a fase do k6.

### 3. Encerramento

Assim que o script `runExperimentReducedQuotas.sh` ou `runExperimentDefaultQuotas.sh` concluir a execução por completo:

* Retorne ao terminal onde o `./collectDockerStats.sh` está em execução.
* **Interrompa manualmente o script `collectDockerStats.sh`** (pressionando `Ctrl + C`) para finalizar a coleta e consolidar o arquivo `docker_monitoring.json`.