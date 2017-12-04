pipeline {
  agent none
  tools {
    maven 'Maven3'
  }
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
    stage('Functional testing') {
      failFast true
      parallel {
        stage('java 7') {
          agent {
            docker {
              image 'openjdk:7-jre'
              label 'centos'
            }
          }
          steps {
            sh "./scripts/artidown.sh org.podvesnoy.rectangle rectangle 1.0-SNAPSHOT"
            sh "java -jar rectangle-1.0-SNAPSHOT.jar 2 4"
          }
        }
        stage('java 8') {
          agent {
            docker {
              image 'openjdk:8-jre'
              label 'centos'
            }
          }
          steps {
            sh "./scripts/artidown.sh org.podvesnoy.rectangle rectangle 1.0-SNAPSHOT"
            sh "java -jar rectangle-1.0-SNAPSHOT.jar 3 4"
          }
        }
        stage('java 9') {
          agent {
            docker {
              image 'openjdk:9-jre'
              label 'centos'
            }
          }
          steps {
            sh "./scripts/artidown.sh org.podvesnoy.rectangle rectangle 1.0-SNAPSHOT"
            sh "java -jar rectangle-1.0-SNAPSHOT.jar 2 6"
          }
        }
      }
    }
  }
}
