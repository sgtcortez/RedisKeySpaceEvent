#!/bin/bash

# This script is used to start the application and configure the redis server to publish key events

docker-compose up -d 
# https://redis.io/docs/manual/keyspace-notifications/#configuration
docker exec -it  key-redis redis-cli config set notify-keyspace-events KEA 1>/dev/null
echo "Done!"