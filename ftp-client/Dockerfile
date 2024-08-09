FROM openjdk:8-jdk-slim

COPY .mvn/ .mvn/
COPY mvnw .
COPY pom.xml .
COPY src/ src/

RUN ./mvnw clean package

COPY src/test/resources/integration-testng.xml integration-testng.xml

ENTRYPOINT ["java", "-jar", "target/ftp-client.jar"]