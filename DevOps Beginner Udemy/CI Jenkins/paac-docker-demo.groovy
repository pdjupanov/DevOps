pipeline {
    agent any
    tools {
        maven "maven3"
        jdk "jdk11"
    }

    environment {
        scannerHome = tool 'sonar6.0'  // Declare scannerHome globally or at the stage level
        registryCredential = 'ecr:eu-central-:ecr_key'
        appRegistry = ".dkr.ecr.eu-central-1.amazonaws.com/vprofile"
        vprofileRegistry = ".dkr.ecr.eu-central-1.amazonaws.com"
    }

    stages {
        stage('Fetch Code') {
            steps {
                git branch: 'docker', url: 'https://github.com/devopshydclub/vprofile-project.git'
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

        stage('Sonar Analysis') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh """
                    ${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=vprofile \
                        -Dsonar.projectName=vprofile \
                        -Dsonar.projectVersion=1.0 \
                        -Dsonar.sources=src/ \
                        -Dsonar.java.binaries=target/test-classes/com/visualpathit/account/controllerTest/ \
                        -Dsonar.junit.reportsPath=target/surefire-reports/ \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                        -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml
                    """
                } 
            }
        }
            stage('Build App Image') {
       steps {
       
         script {
                dockerImage = docker.build( appRegistry + ":$BUILD_NUMBER", "./Docker-files/app/multistage/")
             }

     }
    
    }

    stage('Upload App Image') {
          steps{
            script {
              docker.withRegistry( vprofileRegistry, registryCredential ) {
                dockerImage.push("$BUILD_NUMBER")
                dockerImage.push('latest')
              }
            }
          }
       }  
        
    }  // Closing brace for stages block
}  // Closing brace for pipeline block
