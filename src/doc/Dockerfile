FROM openjdk:8-jdk-alpine
ARG APP_FILE=sky-grpc-receiver-1.0.jar
ENV JAVA_OPTS=""
ENV TZ=Asia/Shanghai
ARG  WORK=/app
ADD  ${APP_FILE} /
RUN  mv  ${APP_FILE} app.jar
WORKDIR ${WORK}
RUN  chmod u+x app.jar
EXPOSE 8080
ENTRYPOINT ["sh","-c","java ${JAVA_OPTS}  -jar app.jar"]