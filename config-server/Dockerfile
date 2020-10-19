FROM adoptopenjdk/openjdk11:alpine-slim as builder
WORKDIR /build

COPY . ./
RUN sh gradlew -console verbose --no-build-cache --no-daemon assemble && mv build/libs/config-server.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM adoptopenjdk/openjdk11:alpine-slim
WORKDIR /opt/app
COPY --from=builder /build/dependencies/ ./
COPY --from=builder /build/spring-boot-loader/ ./
COPY --from=builder /build/application/ ./

WORKDIR /

EXPOSE 8888
EXPOSE 5005

VOLUME /ees-config-server

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT ["java", "-XX:+AlwaysActAsServerClassMachine", "-Djava.security.egd=file:/dev/./urandom", "-cp", "/opt/app","org.springframework.boot.loader.JarLauncher"]
