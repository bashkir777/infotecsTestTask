FROM maven:3.8.4-openjdk-8 AS build

WORKDIR /workspace

COPY pom.xml .

COPY src ./src

RUN mvn clean package

FROM openjdk:8-jdk-slim

COPY --from=build /workspace/target/ target/
COPY src/test/resources/integration-testng.xml integration-testng.xml

ENTRYPOINT ["java", "-jar", "target/ftp-client.jar"]