#!/usr/bin/env groovy

def call(){
  sh "docker build -f Dockerfile -t benya/bp-pythonservice ."
  withCredentials([usernamePassword(credentialsId: 'docker_svc', passwordVariable: 'dockerPass', usernameVariable: 'dockerUser')]) {
    sh "docker login -u ${dockerUser} -p ${dockerPass}"
  }
  sh "docker push benya/bp-pythonservice"
  sh "sed -i 's/image: benya\\/bp-pythonservice/image: benya\\/bp-pythonservice:v0.1/' /root/.kube/bp-pythonservice-deployment.yaml"
  sh "kubectl apply -f /root/.kube/bp-pythonservice-deployment.yaml"
  sh "sed -i 's/image: benya\\/bp-pythonservice:v0.1/image: benya\\/bp-pythonservice/' /root/.kube/bp-pythonservice-deployment.yaml"
  sh "kubectl apply -f /root/.kube/bp-pythonservice-deployment.yaml"
}
