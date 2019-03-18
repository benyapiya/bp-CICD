#!/usr/bin/env groovy

def call(repo) {
  sh "git checkout -B ${env.BRANCH_NAME}"

  withCredentials([usernamePassword(credentialsId: '', passwordVariable: 'gitPass', usernameVariable: 'gitUser')]) {
  sh "git pull https://${gitUser}:${gitPass}@${repo} ${env.BRANCH_NAME} || true"
  sh "git push https://${gitUser}:${gitPass}@${repo} ${env.BRANCH_NAME}"
  }
}


def call(repo, append, credentialsId = '') {
  new_branch = "${env.BRANCH_NAME}_${append}"
  echo "Creating ${new_branch} branch in ${repo}"
  sh "git checkout -B ${new_branch}"

  withCredentials([usernamePassword(credentialsId: credentialsId, passwordVariable: 'gitPass', usernameVariable: 'gitUser')]) {
  sh "git pull https://${gitUser}:${gitPass}@${repo} ${new_branch} || true"
  sh "git push https://${gitUser}:${gitPass}@${repo} ${new_branch}"

  return new_branch
  }
}
