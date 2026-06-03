# Build stage
FROM maven:3.9-eclipse-temurin-26-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:26-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar payment.jar

EXPOSE 8092

ENTRYPOINT ["java", "-jar", "payment.jar"]
