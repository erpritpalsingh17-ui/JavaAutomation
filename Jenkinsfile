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

            emailext(
                subject: "QA Automation - Build ${env.BUILD_NUMBER} PASSED",
                body: """
Hello Product Owner,

QA Automation execution has completed successfully.

Build Number: ${env.BUILD_NUMBER}
Job: ${env.JOB_NAME}
Build URL: ${env.BUILD_URL}

Please check the Extent Test Report in Jenkins.

Regards,
QA Automation
""",
                to: "erpritpalsingh17@gmail.com"
            )
        }

        failure {
            echo 'Automation tests FAILED'

            emailext(
                subject: "QA Automation - Build ${env.BUILD_NUMBER} FAILED",
                body: """
Hello Product Owner,

QA Automation execution has failed.

Build Number: ${env.BUILD_NUMBER}
Job: ${env.JOB_NAME}
Build URL: ${env.BUILD_URL}

Please check the Jenkins console and Extent Test Report for details.

Regards,
QA Automation
""",
                to: "erpritpalsingh17@gmail.com"
            )
        }
    }
}