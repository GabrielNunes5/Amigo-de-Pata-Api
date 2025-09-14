FROM maven:3.9.9-eclipse-temurin-22 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:22-jdk-jammy
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar


EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
