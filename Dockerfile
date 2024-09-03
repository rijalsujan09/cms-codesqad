FROM eclipse-temurin:17-jre-alpine

ARG DOCKER_TAG

ENV BUILD_VERSION=$DOCKER_TAG \
    MODULE_NAME=cms-web

ENV APP_DIR = /opt/cms/web/portal/
ENV APP_NAME = cms-codesqad.jar
WORKDIR $APP_DIR

COPY ./build/libs/cms-codesqad.jar $APP_NAME

CMD ["java", "-jar", "/opt/cms/web/portal/cms-codesqad.jar"]
