pipeline {
  agent any
  tools {
    maven 'Maven3'
  }
  options {
    buildDiscarder(logRotator(numToKeepStr: '2', artifactNumToKeepStr: '1'))
  }

  stages {
    stage('Build') {
      steps {
        sh 'mvn clean package'
      }
    }
    stage('Unit tests') {
      steps {
        sh 'mvn clean test'
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
      junit 'target/surefire-reports/*.xml'
    }
  }
}
