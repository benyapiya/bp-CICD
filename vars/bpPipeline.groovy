#!/usr/bin/env groovy
/*
pass pipeline parameters to Jenkinsfile
bpPipeline (
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
            pr_master = createPRBrach (fullUrl, "master")
            pr_prod = createPRBrach (fullUrl, prod_branch)
            //mergeThenPush(repo: fullUrl, mergeFrom: pr_master, mergeTo: "master", pushTo: pr_master)
            //mergeThenPush(repo: fullUrl, mergeFrom: pr_qa, mergeTo: prod_branch, pushTo: pr_prod)
          }
        }
      }

      stage('Build') {
        steps {
          //parallel(
            //"microservice_build" : {
              // delete container
              // build container from current bode base tag with PR name
              // run container
              //buildMicroservice(${params.pipelineHost})
            //}

          // "pythonservice_build" : {
          //   buildPythonservice(${params.pipelineHost})
          // }
          //)
        }
      }

      stage('Unit Testing') {
        steps {
          //runMicroserviceAPI(${params.prodHost})
        }
      }

      stage('Promote') {
        steps {
          //mergeThenPush(repo: fullUrl, mergeFrom: pr_master, mergeTo: "master", pushTo: "master")
          //mergeThenPush(repo: fullUrl, mergeFrom: pr_prod, mergeTo: prod_branch, pushTo: prod_branch)
          //k8sRolloutMicroservice()
        }
      }

      stage('Prod Validation') {
        steps{
          //runMicroserviceAPI(${params.pipelineHost})
        }
      }

      stage('Cleanup') {
        steps {
          //deletePRBranch(fullUrl, "master")
          //deletePRBranch(fullUrl, prod_branch)
          //echo "CI/CD has completed successfully!"
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
