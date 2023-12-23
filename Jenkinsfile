pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Starting Git up checkout...'
                checkout scm
                echo 'Git checkout successful!'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Owasp-Dependency-check') {
            steps {
            dependencyCheck additionalArguments: '--format HTML', odcInstallation: 'owasp-DC'
            }
        }
        stage('Unit Tests') {
            steps {
                 sh 'mvn test'
            }
        }
        stage('Integration Tests') {
             steps {
                  sh 'mvn verify'
             }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    // Use SonarQube token
                    withCredentials([string(credentialsId: 'sonar', variable: 'SONAR_TOKEN')]) {
                        // Run SonarQube analysis
                             sh "mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN"
                    }
                }
            }
        }

        // Additil stages and steps can be added here
        // For example, you can add stages for build, test, deploy, etc.
    }

    post {
        always {
            echo 'Pipeline completed.'
        }
    }
}
