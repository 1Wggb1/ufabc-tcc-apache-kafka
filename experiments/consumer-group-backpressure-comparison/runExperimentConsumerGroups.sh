START_TIME=$(date "+%Y-%m-%d %H:%M:%S")
echo "[$START_TIME] Starting Consumer group Experiment..."
echo "Starting docker..."
docker compose up --build -d
WAIT_SECONDS=15
echo "Waiting stability... $WAIT_SECONDS seconds"
sleep $WAIT_SECONDS
read -p "Run metrics collect in another terminal (run in this directory the command -> ./collectDockerStats.sh) and after that press [ENTER] to continue..."
METRICS_TIME=$(date "+%Y-%m-%d %H:%M:%S")
echo "[$METRICS_TIME] Starting Metrics Collection..."
sleep 4
echo "Running k6 load test..."
docker run --network=consumer-group-backpressure-comparison_kafka-network --rm -i grafana/k6 run - <k6-load-test-consumer-groups-producer.js
echo "Waiting stability... $WAIT_SECONDS seconds"
sleep $WAIT_SECONDS
END_TIME=$(date "+%Y-%m-%d %H:%M:%S")
echo "[$END_TIME] Consumer group Experiment finished"
exit 0