pipeline {
    agent any
    tools {
        maven 'Maven 3.6.1'
        jdk 'JDK8'
    }
    stages {
        stage('Build') { 
            steps {
              withEnv(["JAVA_HOME=${ tool 'JDK8' }", "PATH+MAVEN=${tool 'Maven 3.6.1'}/bin:${env.JAVA_HOME}/bin"]) {              
                sh "mvn -version"
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