pipeline {
    agent any
    tools {
        maven 'Maven 3.6.1'
        jdk 'jdk8'
    }
    stages {
        stage('Build') { 
            steps {
              sh mvn -version
               sh './build.sh' 
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