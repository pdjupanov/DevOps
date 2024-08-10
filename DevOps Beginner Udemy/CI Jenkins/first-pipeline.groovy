pipeline {
    agent any
    tools {
        maven "maven3"
        jdk "java-JDK-17"
    }

    stages {
        stage('Fetch Code') {
            steps {
                git branch: 'main', url: 'https://github.com/'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn install -DskipTests'
            }
            post {
                success {
                    echo 'Archiving artifacts now.'
                    archiveArtifacts artifacts: '**/*.war'
                }
            }
        }

        stage('Unit Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Checkstyle Analysis') {
            steps {
                sh 'mvn checkstyle:checkstyle'
            }
        }
    }  // <-- Closing brace for stages block

}  // <-- Closing brace for pipeline block
