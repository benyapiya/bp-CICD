#!/usr/bin/env groovy

def call() {
  sh "git checkout -B ${env.BRANCH_NAME}"
  sh "git pull ${env.BRANCH_NAME} || true"
  sh "git push ${env.BRANCH_NAME}"
}
