# stage 1
FROM openjdk:11-jdk-slim AS build

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY *.gradle ./
COPY employers-api/src employers-api/src
COPY employers-api/build.gradle employers-api/build.gradle
COPY employers-db/src employers-db/src
COPY employers-db/build.gradle employers-db/build.gradle
COPY employers-impl/src employers-impl/src
COPY employers-impl/build.gradle employers-impl/build.gradle
RUN ./gradlew --no-daemon bootJar -x test

EXPOSE 8082
EXPOSE 5005

ENTRYPOINT ["java","-XX:+AlwaysActAsServerClassMachine","-Djava.security.egd=file:/dev/./urandom","-jar","employers-impl/build/libs/employers-impl.jar"]
