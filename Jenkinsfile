pipeline {
  agent any
  tools {
    maven 'Maven3'
  }

  stages {
    stage('Build') {
      steps {
        sh 'mvn clean package'
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
    }
  }
}
