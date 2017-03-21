#!groovy

pipeline {
    agent any
    stages {
        stage ('jdk7') {
            steps {
                withMaven(jdk: 'jdk7', maven: 'Maven 3.3.9', mavenSettingsConfig: 'sbforge-nexus') {
                    sh 'mvn -Dmaven.test.failure.ignore=true test'
                }
            }
        }
        stage ('jdk8') {
            steps {
                withMaven(jdk: 'jdk8', maven: 'Maven 3.3.9', mavenSettingsConfig: 'sbforge-nexus') {
                    sh 'mvn -Dmaven.test.failure.ignore=true test'
                }
            }
        }
    }
}