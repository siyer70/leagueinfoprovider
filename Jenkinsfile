pipeline {
    agent any
    tools {
        maven 'Maven 3.6.1'
        jdk 'JDK8'
    }
    environment {
      MY_APP_VERSION = readMavenPom(file: 'inforserver/pom.xml').getVersion()
    }
    stages {
      stage('Build') { 
        steps {
          echo "POM Version is: Infoserver - $MY_APP_VERSION"
          withEnv(["JAVA_HOME=${ tool 'JDK8' }", "PATH+MAVEN=${tool 'Maven 3.6.1'}/bin:${env.JAVA_HOME}/bin:/usr/local/bin"]) {              
            sh "./build.sh"
          }
        }
        post {
           success {
                echo 'Build stage succeeded, now Archiving...'
               }
        } 
      }
      stage('Archive') { 
        steps {
          echo 'Archiving Release artifacts'
          archiveArtifacts artifacts: 'release.tar.gz'
        }
        post {
           success {
                echo 'Archiving completed.'
            }
        }
      }
    } 
}