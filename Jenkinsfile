#!groovy

pipeline {
    agent 'any'
    stages {
        stage ('Checkout') {

            steps {
                checkout scm
            }
        }
        stage('build') {
            agent { docker 'maven:3.3.3-jdk-7' }
            steps {
                withMaven(mavenSettingsConfig: 'sbforge-nexus') {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
        stage('test-7') {
            agent { docker 'maven:3.3.3-jdk-7' }
            steps {
                withMaven(mavenSettingsConfig: 'sbforge-nexus') {
                    sh 'mvn test'
                }
            }
        }
        stage('test-8') {
            agent { docker 'maven:3.3.3-jdk-8' }
            steps {
                withMaven(mavenSettingsConfig: 'sbforge-nexus') {
                    sh 'mvn test'
                }
            }
        }



        stage('integrationtest') {
            steps {
                echo 'Integration Tests'
            }
        }

        stage('deploy') {
            agent { docker 'maven:3.3.3-jdk-7' }

            steps {
                withMaven(mavenSettingsConfig: 'sbforge-nexus') {
                    sh 'mvn install'
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