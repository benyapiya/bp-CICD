#!/usr/bin/env groovy

def call(pipelineHost) {
  sh script: """
    container_id=\$(docker ps | grep bp-microservice | awk '{ print \$1 }')
    if [ ! -z \$container_id ]; then
      docker stop \$container_id
    fi

  """, returnStdout: true

  sh "export M2_HOME=/usr/local/apache-maven/apache-maven-3.6.0;export M2=\$M2_HOME/bin;export PATH=\$M2:$PATH;export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.201.b09-2.el7_6.x86_64;export PATH=\$JAVA_HOME:\$PATH;mvn install"
  sh "docker build -f Dockerfile -t benya/bp-microservice:${env.BRANCH_NAME} ."
  sh "docker run -d -p 8080:8080 -e SA_LOGIC_API_URL='http://${pipelineHost}:5050' benya/bp-microservice:${env.BRANCH_NAME}"
}
