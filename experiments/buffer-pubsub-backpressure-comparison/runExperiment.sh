docker compose up --build -d
sleep 15
bash ./collectDockerStats.sh &
docker run --network=quotas-backpressure-comparison_kafka-network --rm -i grafana/k6 run - <k6-load-test-quotas-producer.js
sleep 15
exit 0