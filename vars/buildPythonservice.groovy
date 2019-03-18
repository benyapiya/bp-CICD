#!/usr/bin/env groovy

def call(pipelineHost) {
  sh "container_id=$(docker ps | grep bp-pythonservice | awk '{ print $1 }');docker stop $container_id"
  sh "docker build -f Dockerfile -t benya/bp-pythonservice:${env.BRANCH_NAME} ."
  sh "docker run -d -p 5050:5000 benya/bp-pythonservice:${env.BRANCH_NAME}"
}
