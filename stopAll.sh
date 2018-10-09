#!/usr/bin/env bash
set -e
# function definition
removeDockerImage()
{
if $(docker images | grep -q $1)
then
    docker images|grep $1|awk '{print $3 }'| xargs docker rmi
fi
}

# Remove existing containers
docker-compose stop
docker-compose rm -f
sleep 2

# Remove old docker images
removeDockerImage springcloudstudy_discovery-service
removeDockerImage springcloudstudy_inventory-service
removeDockerImage springcloudstudy_pricing-service
removeDockerImage springcloudstudy_catalog-service
removeDockerImage springcloudstudy_web-storefront
sleep 2
