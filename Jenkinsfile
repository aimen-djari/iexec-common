pipeline {
    
    agent any

    stages {

        stage('Test') {
            steps {
                withCredentials([
                        string(credentialsId: 'ADDRESS_SONAR', variable: 'address_sonar'),
                        string(credentialsId: 'SONAR_COMMON_TOKEN', variable: 'common_token'),
                        [$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub',
                                usernameVariable: 'DOCKER_IO_USER', passwordVariable: 'DOCKER_IO_PASSWORD']]) {
                    sh 'DOCKER_IO_USER=$DOCKER_IO_USER DOCKER_IO_PASSWORD=$DOCKER_IO_PASSWORD ./gradlew clean test sonarqube -Dsonar.projectKey=iexec-common -Dsonar.host.url=$address_sonar -Dsonar.login=$common_token --no-daemon -i'
                }
                junit 'build/test-results/**/*.xml'
                jacoco()
            }
        }

        stage('Build') {
            steps {
                sh './gradlew build -x test --no-daemon'
            }
        }

        stage('Upload Archive') {
            when {
                anyOf{
                    branch 'master'
                    branch 'develop'
                }
            }
            steps {
                withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'nexus', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASSWORD']]) {
                    sh './gradlew publish -PnexusUser=$NEXUS_USER -PnexusPassword=$NEXUS_PASSWORD --no-daemon'
                }
                archiveArtifacts artifacts: 'build/libs/*.jar'
            }
        }

        stage ("Notify iexec-core") {
            when {
                anyOf{
                    branch 'master'
                    branch 'develop'
                }
            }
            steps {
                build job: 'iexec-core/'+ env.BRANCH_NAME, propagate: true, wait: false
            }
        }

        stage ("Notify iexec-worker") {
            when {
                anyOf{
                    branch 'master'
                    branch 'develop'
                }
            }
            steps {
                build job: 'iexec-worker/'+ env.BRANCH_NAME, propagate: true, wait: false
            }
        }

    }
    
}
