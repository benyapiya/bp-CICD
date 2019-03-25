# bp-CICD

- Build Jenkins server on docker on EC2
- create jenkinsLib
- configure docker to use jenkinsLib
- add credential to jenkins credential (git_svc,docker_svc)
- create JenkinsFile for each repo and call jenkinsLib
- create discovery job to scan for pull request
- if found trigger build/deploy script on cicd container (dedicated environment)
- if pass automatically merge pull request to master
- build/deploy on prod
