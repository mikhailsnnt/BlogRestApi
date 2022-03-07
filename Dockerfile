FROM openjdk:17
ARG JAR_FILE=target/BlogRestApi-1.0.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
