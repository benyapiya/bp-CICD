#!/usr/bin/env groovy

def call(image, tag, proxy ){
    sh "docker build --build-arg HTTP_PROXY=${proxy} --build-arg HTTPS_PROXY=${proxy} -t ${image}:${tag} ."
}
