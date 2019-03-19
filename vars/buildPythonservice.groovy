#!/usr/bin/env groovy

def call(pipelineHost) {
  sh script: """
    container_id=\$(docker ps | grep bp-pythonservice | awk '{ print \$1 }')
    if [ ! -z \$container_id ]; then
      docker stop \$container_id
    fi

  """, returnStdout: true

  sh "docker build -f Dockerfile -t benya/bp-pythonservice:${env.BRANCH_NAME} ."
  sh "docker run -d -p 5050:5000  benya/bp-pythonservice:${env.BRANCH_NAME}"
}
