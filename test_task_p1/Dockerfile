FROM maven:3.8.4-openjdk-8 AS build

WORKDIR /workspace

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:8-jdk-slim

COPY --from=build /workspace/target/test_task_p1.jar test_task_p1.jar

ENTRYPOINT ["java", "-jar", "test_task_p1.jar"]