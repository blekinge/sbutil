pipeline {
    agent { docker 'maven:3.5.0-jdk-7-alpine' }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('test') {
            steps {
                withMaven(mavenSettingsConfig: 'sbforge-nexus') {
                    sh 'mvn clean test || true'
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        stage('build') {
            steps {
                withMaven(mavenSettingsConfig: 'sbforge-nexus') {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
        stage('integrationtest') {
            steps {
                echo 'Integration Tests'
            }
        }
        stage('deploy') {
            steps {
                withMaven(mavenSettingsConfig: 'sbforge-nexus') {
                    echo 'mvn deploy'
                }
            }
        }
    }
    post {
        always {
            echo 'This will always run'
        }
        success {
            echo 'This will run only if successful'
        }
        failure {
            echo 'This will run only if failed'
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}