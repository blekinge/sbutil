#!groovy

pipeline {
    agent any
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage ('Build') {
            steps {
                withMaven(jdk: 'jdk7', maven: 'Maven 3.3.9', mavenSettingsConfig: 'sbforge-nexus') {
                    sh 'mvn -Dmaven.test.failure.ignore=true test'
                }


                withMaven(jdk: 'jdk8', maven: 'Maven 3.3.9', mavenSettingsConfig: 'sbforge-nexus') {
                    sh 'mvn -Dmaven.test.failure.ignore=true test'
                }
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
    }
}