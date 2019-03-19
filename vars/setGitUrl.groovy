#!/usr/bin/env groovy

def call() {
  def repositoryUrl = scm.userRemoteConfigs[0].url
  repositoryUrl = repositoryUrl - 'https://' - '.git'
  scmUrl = repositoryUrl.split('/')
  return scmUrl
}
