ARG APP_NAME=hystrix-dashboard

# build stage
FROM gradle:jdk8 as builder
ADD ./ /build
WORKDIR /build
RUN gradle -console verbose --no-build-cache --no-daemon clean assemble && mv build/libs/*.jar app.jar
RUN jar -xf app.jar

# app stage
FROM bellsoft/liberica-openjdk-alpine-musl:8
COPY --from=builder /build/BOOT-INF/lib /opt/app/lib
COPY --from=builder /build/META-INF /opt/app/META-INF
COPY --from=builder /build/BOOT-INF/classes /opt/app

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
RUN chmod +x /wait

ARG APP_NAME
RUN mkdir /opt/log

EXPOSE 8788
EXPOSE 5005

CMD /wait && java -XX:+AlwaysActAsServerClassMachine -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -cp opt/app:opt/app/lib/* ru.skubatko.dev.ees.hystrix.dashboard.HystrixDashboardApp --logging.file=/opt/log/${APP_NAME}.log
