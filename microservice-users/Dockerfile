ARG APP_NAME=microservice-users

# build stage
FROM gradle:jdk11 as builder
ADD ./ /build
WORKDIR /build
RUN gradle -console verbose --no-build-cache --no-daemon clean assemble && mv users-impl/build/libs/users-impl*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# app stage
FROM bellsoft/liberica-openjdk-alpine-musl:11
WORKDIR /opt/app
COPY --from=builder /build/dependencies/ ./
COPY --from=builder /build/spring-boot-loader/ ./
COPY --from=builder /build/application/ ./

WORKDIR /

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
RUN chmod +x /wait

ARG APP_NAME
RUN mkdir /opt/log

EXPOSE 8081
EXPOSE 5005

CMD /wait && java -XX:+AlwaysActAsServerClassMachine -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -cp /opt/app org.springframework.boot.loader.JarLauncher --logging.file=/opt/log/${APP_NAME}.log
