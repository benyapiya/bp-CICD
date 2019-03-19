#!/usr/bin/env groovy
def call(repo, toBranch) {
  withCredentials([usernamePassword(credentialsId: 'git_svc', passwordVariable: 'gitPass', usernameVariable: 'gitUser')]) {
    sh "git checkout ${toBranch}"
    sh "git pull https://${gitUser}:${gitPass}@${repo} ${toBranch}"
    sh "git merge ${env.BRANCH_NAME} --no-edit"
    sh "git push https://${gitUser}:${gitPass}@${repo} ${env.BRANCH_NAME}"
  }
}

def call(repo, toBranch, remoteBranch) {
  withCredentials([usernamePassword(credentialsId: 'git_svc', passwordVariable: 'gitPass', usernameVariable: 'gitUser')]) {
    sh "git checkout ${toBranch}"
    sh "git pull https://${gitUser}:${gitPass}@${repo} ${toBranch}"
    sh "git merge ${env.BRANCH_NAME} --no-edit"
    sh "git push https://${gitUser}:${gitPass}@${repo} ${remoteBranch}"
  }
}
