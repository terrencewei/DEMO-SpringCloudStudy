#!/usr/bin/env bash
SPRING_APPLICATION_JSON='{"eureka":{"client":{"serviceUrl":{"defaultZone":"http://172.17.118.200:7000/eureka"}}}}' gradle clean bootRun