pipeline {
    agent any

    stages {

        stage('Install Dependencies') {
            steps {
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
        }

        success {
            echo 'Automation tests PASSED'
        }

        failure {
            echo 'Automation tests FAILED'
        }
    }
}