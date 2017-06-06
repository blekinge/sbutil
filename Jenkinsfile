#!groovy

pipeline {
    agent { docker 'maven:3.3.3' }
    stages {
        stage('build') {
            steps {
                withMaven(
                        mavenSettingsConfig: '24fc2be7-24c9-4d01-8e06-b1c662c231a1',
                ) {
                    sh 'mvn install'
                }
            }
        }
    }
}