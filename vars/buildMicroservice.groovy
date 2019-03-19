#!/usr/bin/env groovy

def call(pipelineHost) {
  sh "container_id=$(docker ps | grep bp-microservice | awk '{ print $1 }');docker stop $container_id"
  sh "mvn install"
  sh "docker build -f Dockerfile -t benya/bp-microservice:${env.BRANCH_NAME} ."
  sh "docker run -d -p 8080:8080 -e SA_LOGIC_API_URL='http://${pipelineHost}:5050' benya/bp-microservice:${env.BRANCH_NAME}"
}
