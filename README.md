# bp-CICD

- Build Jenkins server on docker on EC2
`$ docker build -f Dockerfile -t benya/bp-jenkins .`
`$ docker volume create jenkins-data`
`$ docker run -u root --rm -d -p 8080:8080 -p 50000:50000 -v jenkins-data:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker benya/bp-jenkins`

- create jenkinsLib
- configure docker to use jenkinsLib
- add credential to jenkins credential (git_svc,docker_svc)
- create JenkinsFile for each repo and call jenkinsLib
- create discovery job to scan for pull request
- if found trigger build/deploy script on cicd container (dedicated environment)
- if pass automatically merge pull request to master
- build/deploy on prod
