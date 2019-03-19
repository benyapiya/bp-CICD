#!/usr/bin/env groovy
import groovy.json.JsonSlurperClassic
@NonCPS
def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}

def call(microserviceHost){
  RESULT = sh (
  script: """curl -X POST -H 'Content-Type: application/json'\
      -d '{ "sentence": "hay"}' \
      http://${microserviceHost}:9090/sentiment""",
  returnStdout: true
  ).trim()

  RESULT = jsonParse(RESULT)

  if (RESULT.polarity != 'yah') {
    throw new Exception("VALIDATION FAILED")
  } else {
    echo "runMicroservice API passed"
  }
}
