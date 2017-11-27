pipeline {
  agent none
  tools {
    maven 'Maven3'
  }
  tool name: 'docker-latest', type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
  options {
    buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
  }

  stages {
    stage('Unit tests') {
      agent { label 'master'}
      steps {
        sh 'mvn clean test'
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
        }
      }
    }
    stage('Build') {
      agent { label 'centos'}
      steps {
        sh 'mvn clean package'
      }
      post {
        always {
          archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
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
}
