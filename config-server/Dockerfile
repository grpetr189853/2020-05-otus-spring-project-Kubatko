ARG APP_NAME=config-server

# build stage
FROM gradle:jdk11 as builder
ADD ./ /build
WORKDIR /build
ARG APP_NAME
RUN gradle -console verbose --no-build-cache --no-daemon clean assemble && mv build/libs/${APP_NAME}*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# app stage
FROM bellsoft/liberica-openjdk-alpine-musl:11
WORKDIR /opt/app
COPY --from=builder /build/dependencies/ ./
COPY --from=builder /build/spring-boot-loader/ ./
COPY --from=builder /build/application/ ./

WORKDIR /

ARG APP_NAME
RUN mkdir /opt/log

EXPOSE 8888
EXPOSE 5005

ENTRYPOINT exec java -XX:+AlwaysActAsServerClassMachine -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -cp /opt/app org.springframework.boot.loader.JarLauncher --logging.file=/opt/log/${APP_NAME}.log
