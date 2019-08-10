pipeline {
    agent any

    stages {
        stage('Build') { 
            steps {
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