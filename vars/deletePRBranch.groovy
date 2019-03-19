#!/usr/bin/env groovy
def call(repo){
  withCredentials([usernamePassword(credentialsId: 'git_svc', passwordVariable: 'gitPass', usernameVariable: 'gitUser')]) {
    sh "git push --delete https://${gitUser}:${gitPass}@${repo} ${env.BRANCH_NAME}"
  }
}
