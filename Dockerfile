FROM cloudbees/cloudbees-jenkins-distribution
USER root
RUN apt-get update -y
RUN apt-get install -y maven
RUN apt-get update -y
RUN apt-get install -y apt-transport-https
RUN curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg |  apt-key add -
RUN echo "deb https://apt.kubernetes.io/ kubernetes-xenial main" |  tee -a /etc/apt/sources.list.d/kubernetes.list
RUN apt-get update -y
RUN apt-get install -y kubectl
RUN mkdir -p /root/.kube/
RUN echo 'apiVersion: extensions/v1beta1\n\
kind: Deployment\n\
metadata:\n\
  name: bp-pythonservice\n\
spec:\n\
  replicas: 2\n\
  minReadySeconds: 15\n\
  strategy:\n\
    type: RollingUpdate\n\
    rollingUpdate:\n\
      maxUnavailable: 1\n\
      maxSurge: 1\n\
  template:\n\
    metadata:\n\
      labels:\n\
        app: bp-pythonservice\n\
    spec:\n\
      containers:\n\
        - image: benya/bp-pythonservice\n\
          imagePullPolicy: Always\n\
          name: bp-pythonservice\n\
          ports:\n\
            - containerPort: 5000\n'\
>> /root/.kube/bp-pythonservice-deployment.yaml

RUN echo 'apiVersion: v1\n\
kind: Config\n\
clusters:\n\
- cluster:\n\
    api-version: v1\n\
    insecure-skip-tls-verify: true\n\
    server: "https://18.216.225.187:8080/r/projects/1a7/kubernetes:6443"\n\
  name: "bp"\n\
contexts:\n\
- context:\n\
    cluster: "bp"\n\
    user: "bp"\n\
  name: "bp"\n\
current-context: "bp"\n\
users:\n\
- name: "bp"\n\
  user:\n\
    token: "k8s_token"\n'\
>> /root/.kube/config
