pipeline {
    agent any
    tools {
        maven 'Maven 3.6.1'
        jdk 'JDK8'
    }
    stages {
        stage('Build') { 
            steps {
              def mvn_version = 'Maven 3.6.1'
              withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {              
                sh mvn -version
              }
            }
            post {
               success {
                    echo 'Now Archiving...'
                   }
            } 
        }
        stage('Archive') { 
            steps {
              archiveArtifacts 'release'
            }
            post {
               success {
                    echo 'Archiving completed.'
                }
            } 
        }

    }
}