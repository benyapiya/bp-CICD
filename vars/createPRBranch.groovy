#!/usr/bin/env groovy

def call(repo) {
  //Create a local branch named the same as the PR
  sh "git checkout -B ${env.BRANCH_NAME}"

  withCredentials([usernamePassword(credentialsId: 'git_svc', passwordVariable: 'gitPass', usernameVariable: 'gitUser')]) {
  // If this branch already existed, pull the lates. If not, skip the error
  sh "git pull https://${gitUser}:${gitPass}@${repo} ${env.BRANCH_NAME} || true"
  // Push the local branch to github
  sh "git push https://${gitUser}:${gitPass}@${repo} ${env.BRANCH_NAME}"
  }
}
