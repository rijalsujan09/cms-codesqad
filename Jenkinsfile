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
                echo 'Unit Tests Passed!'
            }
        }

        stage ('Compile') {
            steps {
                            sh '''
                                docker run --rm -v $(pwd):/tmp gradle:jdk17 bash -c "
                                    cd /tmp &&
                                    chmod +x ./gradlew &&
                                    ./gradlew build
                                "
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

        stage ('Scan') {
            steps {
                            // Run Trivy to scan the built Docker image
                            sh '''
                                docker run --rm \
                                    -v /var/run/docker.sock:/var/run/docker.sock \
                                    -v $(pwd)/trivy-reports:/root/.cache/ \
                                    aquasec/trivy:latest image \
                                    --format table  \
                                    --output trivy-report.txt \
                                    rijalsujan09/cms-codesqad:latest

                            '''
                        }

                        post {
                            always {
                                archiveArtifacts artifacts: 'trivy-report.txt', allowEmptyArchive: true
                            }
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
}
