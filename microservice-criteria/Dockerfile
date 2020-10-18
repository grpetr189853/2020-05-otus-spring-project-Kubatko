FROM openjdk:11-jdk-slim AS build

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY *.gradle ./
COPY criteria-api/src criteria-api/src
COPY criteria-api/build.gradle criteria-api/build.gradle
COPY criteria-db/src criteria-db/src
COPY criteria-db/build.gradle criteria-db/build.gradle
COPY criteria-impl/src criteria-impl/src
COPY criteria-impl/build.gradle criteria-impl/build.gradle
RUN ./gradlew --no-daemon bootJar -x test

EXPOSE 8083
EXPOSE 5005

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
RUN chmod +x /wait

CMD /wait && java -XX:+AlwaysActAsServerClassMachine -Djava.security.egd=file:/dev/./urandom -jar criteria-impl/build/libs/criteria-impl.jar
