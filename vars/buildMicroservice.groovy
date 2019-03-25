#!/usr/bin/env groovy

def call(pipelineHost) {
  sh script: """
    container_id=\$(docker ps | grep bp-microservice | awk '{ print \$1 }')
    if [ ! -z \$container_id ]; then
      docker stop \$container_id
    fi

  """, returnStdout: true

  sh "mvn install"
  sh "docker build -f Dockerfile -t benya/bp-microservice:${env.BRANCH_NAME} ."
  sh "docker run -d -p 90:8080 -e SA_LOGIC_API_URL='http://${pipelineHost}:5050' benya/bp-microservice:${env.BRANCH_NAME}"
}
