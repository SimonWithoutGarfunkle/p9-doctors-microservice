# BUILD STAGE
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# PACKAGE STAGE
FROM openjdk:17
COPY --from=build /app/target/doctors-0.0.1-SNAPSHOT.jar doctors.jar
EXPOSE 9002
CMD ["java","-jar","doctors.jar"]