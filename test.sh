#!/usr/bin/env bash
set -e
# config
export EUREKA_SERVER_IP=172.17.118.200
export EUREKA_SERVER_PORT=8081

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

# stop
removeDockerImage()
{
if $(docker images | grep -q $1)
then
    docker images|grep $1|awk '{print $3 }'| xargs docker rmi
fi
}

# Remove existing containers
docker-compose stop catalog-service discovery-service
docker-compose rm -f catalog-service discovery-service
sleep 2

# Remove old docker images
removeDockerImage springcloudstudy_discovery-service
removeDockerImage springcloudstudy_catalog-service
sleep 2


# Build the project and docker images
gradle clean bootJar;

# Build new docker images
buildDockerImage springcloudstudy_discovery-service discovery-service
buildDockerImage springcloudstudy_catalog-service catalog-service
sleep 2
# Start the discovery service next and wait
docker-compose up -d discovery-service

while [ -z ${DISCOVERY_SERVICE_READY} ]; do
  echo "Waiting for discovery service..."
  if [ "$(curl --silent $EUREKA_SERVER_IP:$EUREKA_SERVER_PORT/actuator/health 2>&1 | grep -q '\"status\":\"UP\"'; echo $?)" = 0 ]; then
      DISCOVERY_SERVICE_READY=true;
  fi
  sleep 2
done

# Start the other service containers
docker-compose up -d catalog-service
