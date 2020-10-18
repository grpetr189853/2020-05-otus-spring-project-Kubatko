# stage 1
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
RUN mkdir -p criteria-impl/build/dependency && (cd criteria-impl/build/dependency; jar -xf ../libs/*.jar)

# stage 2
FROM openjdk:11-jdk-slim

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

VOLUME /tmp
ARG DEPENDENCY=/workspace/app/criteria-impl/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8083
EXPOSE 5005

ENTRYPOINT ["java","-XX:+AlwaysActAsServerClassMachine","-Djava.security.egd=file:/dev/./urandom","-cp","app:app/lib/*","ru.skubatko.dev.ees.criteria.CriteriaServiceApp"]
