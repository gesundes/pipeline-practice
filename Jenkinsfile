pipeline {
  agent none
  tools {
    maven 'Maven3'
  }
  options {
    buildDiscarder(logRotator(numToKeepStr: '2', artifactNumToKeepStr: '1'))
  }

  stages {
    stage('Unit tests') {
      agent { label 'master' }
      steps {
        sh 'mvn clean test'
      }
    }
    stage('Build') {
      agent { label 'centos' }
      steps {
        sh 'mvn clean package'
      }
    }
    stage('Upload') {
      agent {
        docker {
          image 'openjdk:8-jre'
          label 'centos'
        }
      }
      steps {
        nexusArtifactUploader artifacts: [[artifactId: 'rectangle', classifier: '', file: 'target/rectangle-1.0-SNAPSHOT.jar', type: 'jar']], credentialsId: 'nexus', groupId: 'org.podvesnoy.rectangle', nexusUrl: '10.0.2.10:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'maven-snapshots', version: '1.0-SNAPSHOT'
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
