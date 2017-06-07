#!groovy

pipeline {
    agent none
    stages {
        stage('Checkout') {
            agent any
            steps {
                checkout scm
            }
        }
        stage('test on java 8') {
            agent any
            steps {
                parallel(
                        "Java7": {
                            node('java7') {
                                docker.image('maven:3.3.3-jdk-7').inside {
                                    withMaven(mavenSettingsConfig: 'sbforge-nexus') {
                                        sh 'mvn test'
                                    }
                                }
                            }
                        },
                        "Java8": {
                            docker.image('maven:3.3.3-jdk-8').inside {
                                withMaven(mavenSettingsConfig: 'sbforge-nexus') {
                                    sh 'mvn test'
                                }
                            }
                        }
                )
            }
            post {
                always {
                    junit '**/target/*.xml'
                }
            }
        }
        stage('test on java 7') {
            agent { docker 'maven:3.3.3-jdk-7' }
            steps {
                withMaven(mavenSettingsConfig: 'sbforge-nexus') {
                    sh 'mvn test'
                }
            }
            post {
                always {
                    junit '**/target/*.xml'
                }
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
        stage('integrationtest') {
            agent any
            steps {
                echo 'Integration Tests'
            }

        }
        stage('deploy') {
            agent { docker 'maven:3.3.3-jdk-7' }
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