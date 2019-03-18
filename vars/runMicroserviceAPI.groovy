#!/usr/bin/env groovy

def call(microserviceHost){
  RESULT = sh (
  script: """curl -X POST -H 'Content-Type: application/json'\
      -d '{ "sentence": "hay"}' \
      https://${microserviceHost}/reverse_sentence""",
  returnStdout: true
  ).trim()

  RESULT = jsonParse(CERT)

  if (RESULT.polarity != 'yah') {
    throw new Exception("VALIDATION FAILED")
  } else {
    echo "runMicroservice API passed"
  }
}
