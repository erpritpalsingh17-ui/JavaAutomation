pipeline {
    agent any

    environment {
        PATH = "/opt/homebrew/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin"
    }

    stages {

        stage('Install Dependencies') {
            steps {
                sh 'mvn -version'
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Run Automation Tests') {
            steps {
                sh 'mvn test'
            }
        }
    }

    post {
        always {
            echo 'Test execution completed'
             publishHTML([
            reportDir: 'reports',
            reportFiles: 'ExtentReport.html',
            reportName: 'Extent Test Report',
            keepAll: true,
            alwaysLinkToLastBuild: true,
            allowMissing: true
        ])
        }

        success {
            echo 'Automation tests PASSED'
        }

        failure {
            echo 'Automation tests FAILED'
        }
    }
}