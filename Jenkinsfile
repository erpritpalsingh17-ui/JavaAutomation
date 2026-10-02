pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/YOUR_USERNAME/qa-automation.git'
            }
        }

        stage('Install Dependencies') {
            steps {
                sh 'npm install'
            }
        }

        stage('Run Automation Tests') {
            steps {
                sh 'npx playwright test'
            }
        }
    }

    post {

        always {
            echo 'Test execution completed'
        }

        success {
            echo 'Automation tests PASSED'
        }

        failure {
            echo 'Automation tests FAILED'
        }
    }
}