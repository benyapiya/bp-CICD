#!/usr/bin/env groovy

def call(){
  sh "mvn install"
  sh "docker build -f Dockerfile -t benya/bp-microservice ."
  sh "sed -i 's/image: benya/bp-pythonservice/image: benya/bp-pythonservice:v0.1' /root/.kube/config/bp-pythonservice-deployment.yaml"
  sh "kubectl apply -f /root/.kube/config/bp-pythonservice-deployment.yaml"
  sh "sed -i 's/image: benya/bp-pythonservice:v0.1/image: benya/bp-pythonservice' /root/.kube/config/bp-pythonservice-deployment.yaml"
  sh "kubectl apply -f /root/.kube/config/bp-pythonservice-deployment.yaml"
}
