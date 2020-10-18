# stage 1
FROM openjdk:11-jdk-slim AS build

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY *.gradle ./
COPY src src
RUN ./gradlew -console verbose --no-build-cache --no-daemon assemble -x test
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

# stage 2
FROM openjdk:11-jdk-slim

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8080
EXPOSE 5005

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
RUN chmod +x /wait

CMD /wait && java -XX:+AlwaysActAsServerClassMachine -Djava.security.egd=file:/dev/./urandom -cp app:app/lib/* ru.skubatko.dev.ees.ui.EesMainApp
