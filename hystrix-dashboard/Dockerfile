# stage 1
FROM openjdk:8-jdk-alpine AS build

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY *.gradle ./
COPY src src
RUN ./gradlew --no-daemon bootJar -x test
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

# stage 2
FROM openjdk:8-jdk-alpine

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8788
EXPOSE 5005

ENTRYPOINT ["java","-cp","app:app/lib/*","ru.skubatko.dev.ees.hystrix.dashboard.HystrixDashboardApp"]
