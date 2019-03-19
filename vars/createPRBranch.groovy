#!/usr/bin/env groovy

def call(repo) {
  withCredentials([usernamePassword(credentialsId: 'git_svc', passwordVariable: 'gitPass', usernameVariable: 'gitUser')]) {
  sh "git push --delete https://${gitUser}:${gitPass}@${repo} ${env.BRANCH_NAME} || true"
  sh "git checkout -B ${env.BRANCH_NAME}"
  sh "git pull https://${gitUser}:${gitPass}@${repo} ${env.BRANCH_NAME} || true"
  sh "git push https://${gitUser}:${gitPass}@${repo} ${env.BRANCH_NAME}"
  }
}
