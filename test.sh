#!/usr/bin/env bash
set -e
# config

buildDockerImage()
{
docker build \
-t $1 \
-f ${PWD}/$2/src/main/docker/DockerFile \
${PWD}/$2/build/libs/;
}

# Export the active docker machine IP
export DOCKER_IP=$(docker-machine ip $(docker-machine active))

# docker-machine doesn't exist in Linux, assign default ip if it's not set
DOCKER_IP=${DOCKER_IP:-0.0.0.0}

# stop and remove all exist images firstly
sh stopAll.sh

# Build the project and docker images
gradle clean bootJar bootWar;

# Build new docker images
buildDockerImage springcloudstudy_discovery-service discovery-service
buildDockerImage springcloudstudy_catalog-service catalog-service
sleep 2
# Start the discovery service next and wait
docker-compose up -d catalog-service
