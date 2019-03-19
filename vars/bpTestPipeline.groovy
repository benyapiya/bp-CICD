#!/usr/bin/env groovy
/*
pass pipeline parameters to Jenkinsfile
bpTestPipeline (
  pipelineHost: "ip_of_host_to_run_pipeline",
  prodHost: "ip_of_production_host",
  email: "your_email"
)
*/
def call(Map params){
  pipeline {
    agent { label 'master' }
    // Pull request has been cloned and merged by jenkins
    environment {
      prod_branch = 'prod'
    }

    stages {
      stage('Init') {
        steps {
          script{
            // Create PR branches for testing
            scmUrl = setGitUrl()
            org = scmUrl[1]
            repo = scmUrl[2]
            fullUrl = "${scmUrl[0]}/${org}/${repo}"
            createPRBranch(fullUrl)
          }
        }
      }
      stage('Build') {
        steps {
          // delete container
          // build container from current bode base tag with PR name
          // run container
          buildPythonservice("${params.pipelineHost}")
          sleep 10
        }
      }
      stage('Unit Testing') {
        steps {
          runPythonserviceAPI("${params.pipelineHost}")
        }
      }
      stage('Promote') {
        steps {
          mergeThenPush(fullUrl, "master")
          mergeThenPush(fullUrl, "master", "master")
          k8sRolloutMicroservice()
        }
      }
      stage('Prod Validation') {
        steps{
          runMicroserviceAPI("${params.prodHost}")
        }
      }
      stage('Cleanup') {
        steps {
          deletePRBranch(fullUrl)
          echo "CI/CD has completed successfully!"
        }
      }
    }
  }
}
