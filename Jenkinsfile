#!groovy

pipeline {
    agent { docker 'maven:3.3.3' }
    stages {
        stage('build') {
            steps {
                withMaven(
                        mavenSettingsConfig: 'my-settings',
                ) {
                    sh 'mvn'
                }
            }
        }
    }
}