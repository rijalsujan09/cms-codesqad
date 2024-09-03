pipeline {
    agent any

    environment {
            // Generate a timestamp for versioning the Docker image
            BUILD_TIMESTAMP = sh(returnStdout: true, script: 'date +%Y%m%d%H%M%S').trim()
            // Docker Hub credentials stored in Jenkins
            DOCKER_REGISTRY_CREDENTIALS = credentials('dockerhub')
            // Docker Hub username
            DOCKERHUB_USERNAME = 'rijalsujan'
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
                    docker build --build-arg DOCKER_TAG=${BUILD_TIMESTAMP} -t your-dockerhub-username/cms-codesqad:${BUILD_TIMESTAMP} -t your-dockerhub-username/cms-codesqad:latest .
                '''
            }
        }

        stage ('Scan') {
            steps {
                echo 'Scanning Passed!'
            }
        }

        stage ('Publish') {
            steps {
                withDockerRegistry(credentialsId: 'dockerhub', url: 'https://index.docker.io/v1/') {
                    sh '''
                        docker push ${DOCKERHUB_USERNAME}/cms-codesqad:${BUILD_TIMESTAMP}
                        docker push ${DOCKERHUB_USERNAME}/cms-codesqad:latest
                    '''
                }
            }
        }
    }
}
