#!/bin/sh

#while ! nc -z config-server 9090 ; do
#    echo "Waiting for the Config Server"
#    sleep 3
#done

docker-compose up -d discovery
while ! nc -z localhost 8761 ; do
    echo "Waiting for the Eureka Server"
    sleep 3
done

docker-compose up -d
