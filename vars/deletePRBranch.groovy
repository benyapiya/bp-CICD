#!/usr/bin/env groovy
def call(repo){
    sh "git push --delete ${env.BRANCH_NAME}"
}
