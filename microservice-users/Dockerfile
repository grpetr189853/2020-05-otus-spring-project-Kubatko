FROM openjdk:11-jdk-slim

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY *.gradle ./
COPY users-api/src users-api/src
COPY users-api/build.gradle users-api/build.gradle
COPY users-db/src users-db/src
COPY users-db/build.gradle users-db/build.gradle
COPY users-impl/src users-impl/src
COPY users-impl/build.gradle users-impl/build.gradle
RUN ./gradlew --no-daemon bootJar -x test

EXPOSE 8081
EXPOSE 5005

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
RUN chmod +x /wait

CMD /wait && java -XX:+AlwaysActAsServerClassMachine -Djava.security.egd=file:/dev/./urandom -jar users-impl/build/libs/users-impl.jar
