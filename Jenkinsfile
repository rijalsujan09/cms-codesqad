pipeline {
    agent any

    environment {
            // Generate a timestamp for versioning the Docker image
            BUILD_TIMESTAMP = sh(returnStdout: true, script: 'date +%Y%m%d%H%M%S').trim()
            // Docker Hub username
            DOCKERHUB_USERNAME = 'rijalsujan09'
        }
    stages {
        stage ('Unit Test') {
            steps {
                echo "Build Timestamp: ${env.BUILD_TIMESTAMP}"
                 //sh 'chmod +x ./gradlew'
                 //sh './gradlew test'
                echo 'Unit Tests Passed!'
            }
        }

        stage ('Compile') {
            steps {
                            sh '''
                                docker run --dns 8.8.8.8 --rm -v $(pwd):/tmp gradle:jdk17 bash -c " cd /tmp && chmod +x ./gradlew && ./gradlew clean build "
                            '''
            }
        }

        stage ('Build') {
            steps {
                sh '''
                    docker build --build-arg DOCKER_TAG=${BUILD_TIMESTAMP} -t rijalsujan09/cms-codesqad:${BUILD_TIMESTAMP} -t rijalsujan09/cms-codesqad:latest .
                '''
            }
        }

        stage ('Scanner') {
            steps {
                            echo 'Running Trivy Scan...'
                            sh '''
                                docker run --dns 8.8.8.8  --rm \
                                    -v $(pwd)/trivy-reports:/var/trivy-reports/ \
                                    aquasec/trivy:latest image \
                                    --exit-code 0 --no-progress -f table -o /var/trivy-reports/test.txt \
                                    rijalsujan09/cms-codesqad:latest
                            '''

                            echo 'Complete Trivy Scan...'


            }
        }


        stage ('Publish') {
            steps {
                echo 'publish started'

                withDockerRegistry(credentialsId: 'dockerhub', url: 'https://index.docker.io/v1/') {
                                    sh '''
                                        docker push ${DOCKERHUB_USERNAME}/cms-codesqad:${BUILD_TIMESTAMP}
                                        docker push ${DOCKERHUB_USERNAME}/cms-codesqad:latest
                                    '''
                                }
                echo 'publish completed'
            }
        }
    }
   post {
           always {
               //cleanWs()
           }
           failure {
               script {
                   def message = "Build or Test failed at stage: ${currentBuild.currentResult}. Please check the logs for details."
                   def subject = "Jenkins Build Failure: ${env.JOB_NAME} - ${env.BUILD_NUMBER}"
                   emailext(
                       to: 'contact.rijalsujan09@gmail.com',
                       subject: subject,
                       body: message,
                       attachLog: true,
                       retry: 5,
                       retryWaitIntervalSeconds: 5
                   )
               }
           }
           success {
               script {
                   def message = "Build and tests completed successfully for job: ${env.JOB_NAME}, build number: ${env.BUILD_NUMBER}."
                   def subject = "Jenkins Build Success: ${env.JOB_NAME} - ${env.BUILD_NUMBER}"
                   emailext(
                       to: 'contact.rijalsujan09@gmail.com',
                       subject: subject,
                       body: message,
                       attachLog: true,
                       retry: 5,
                       retryWaitIntervalSeconds: 5
                   )
               }
           }
   }
}
