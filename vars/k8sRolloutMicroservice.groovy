#!/usr/bin/env groovy

def call(){
  sh "docker build -f Dockerfile -t benya/bp-microservice ."
  sh "docker push benya/bp-microservice"
  sh "sed -i 's/image: benya\\/bp-microservice/image: benya\\/bp-pythonservice:v0.1/' /root/.kube/bp-microservice-deployment.yaml"
  sh "kubectl apply -f /root/.kube/bp-pythonservice-deployment.yaml"
  sh "sed -i 's/image: benya\\/bp-microservice:v0.1/image: benya\\/bp-microservice/' /root/.kube/bp-microservice-deployment.yaml"
  sh "kubectl apply -f /root/.kube/bp-microservice-deployment.yaml"
}
