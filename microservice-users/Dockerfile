FROM adoptopenjdk/openjdk11:alpine-slim as builder
WORKDIR /build

COPY . ./
RUN sh gradlew -console verbose --no-build-cache --no-daemon assemble && mv users-impl/build/libs/users-impl.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM adoptopenjdk/openjdk11:alpine-slim
WORKDIR /opt/app
COPY --from=builder /build/dependencies/ ./
COPY --from=builder /build/spring-boot-loader/ ./
COPY --from=builder /build/application/ ./

WORKDIR /

EXPOSE 8081
EXPOSE 5005

VOLUME /ees-users

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
RUN chmod +x /wait

CMD /wait && java -XX:+AlwaysActAsServerClassMachine -Djava.security.egd=file:/dev/./urandom -cp /opt/app org.springframework.boot.loader.JarLauncher
