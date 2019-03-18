#!/usr/bin/env groovy
def call(repo, toBranch) {
    sh "git checkout ${toBranch}"
    sh "git pull ${toBranch}"
    sh "git merge ${env.BRANCH_NAME} --no-edit"
    sh "git push ${env.BRANCH_NAME}"
}

def call(repo, toBranch, remoteBranch) {
    sh "git checkout ${toBranch}"
    sh "git pull ${toBranch}"
    sh "git merge ${env.BRANCH_NAME} --no-edit"
    sh "git push ${remoteBranch}"
}
