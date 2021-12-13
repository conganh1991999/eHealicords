# syntax = docker/dockerfile:1.0-experimental

FROM maven:3.6.3-jdk-11 as builder
WORKDIR /ehealicords

COPY ./pom.xml .
COPY ./src/ ./src/

RUN --mount=type=cache,target=/root/.m2 ["mvn", "-Djar.finalName=ehealicords", "package"]

FROM openjdk:11.0.4-jre-slim
WORKDIR /ehealicords

COPY --from=builder /ehealicords/target/*.jar ./ehealicords.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ehealicords.jar"]
