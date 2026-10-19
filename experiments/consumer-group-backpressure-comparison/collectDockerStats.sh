while true; do
  TIMESTAMP=$(date "+%Y-%m-%d %H:%M:%S")
  docker stats --no-stream --format '{"timestamp":"'"$TIMESTAMP"'","container":"{{.Container}}","name":"{{.Name}}","cpu":"{{.CPUPerc}}","mem":"{{.MemPerc}}"}' >> docker_monitoring.json
  sleep 2
done