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
RUN mkdir -p employers-impl/build/dependency && (cd employers-impl/build/dependency; jar -xf ../libs/*.jar)

# stage 2
FROM openjdk:11-jdk-slim

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

VOLUME /tmp
ARG DEPENDENCY=/workspace/app/employers-impl/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8082
EXPOSE 5005

ENTRYPOINT ["java","-XX:+AlwaysActAsServerClassMachine","-Djava.security.egd=file:/dev/./urandom","-cp","app:app/lib/*","ru.skubatko.dev.ees.employers.EmployersServiceApp"]
