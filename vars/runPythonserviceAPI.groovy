#!/usr/bin/env groovy
import groovy.json.JsonSlurperClassic
@NonCPS
def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}

def call(pythonserviceHost){
  RESULT = sh (
  script: """curl -X POST -H 'Content-Type: application/json'\
      -d '{ "sentence": "hay"}' \
      http://${pythonserviceHost}:5050/reverse_sentence""",
  returnStdout: true
  ).trim()

  RESULT = jsonParse(RESULT)

  if (RESULT.polarity != 'yah') {
    throw new Exception("VALIDATION FAILED")
  } else {
    echo "runPythonservice API passed"
  }
}
