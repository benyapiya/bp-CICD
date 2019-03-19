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
            pr_master = createPRBranch(fullUrl)
          }
        }
      }

    }
    post {
      always {
        emailext attachLog: true, to: '${params.email}', subject: "${env.JOB_NAME} : #${env.BUILD_NUMBER}", body: "Body"
      }
    }
  }
}
