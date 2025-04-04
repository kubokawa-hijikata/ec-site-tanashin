# Stage 1: Build the application 
FROM maven:3.8.8-eclipse-temurin-21 AS build 

# Set the working directory 
WORKDIR /app  

# Copy Maven's configuration and install dependencies 
COPY pom.xml /app/
COPY src /app/src

# Build the application (creates a JAR file in the /target directory) 
RUN mvn clean package -DskipTests 

# Stage 2: Run the application 
FROM eclipse-temurin:21-jdk-jammy

# Set the working directory 
WORKDIR /app 

# Copy the JAR file from the previous stage 
COPY --from=build /app/target/*.jar /app/app.jar 

# Expose the port that the app runs on 
EXPOSE 8080
EXPOSE 5005

# Run the Spring Boot application 
ENTRYPOINT ["java","-Dspring.config.location=file:/app/config/application.properties","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","-jar","/app/app.jar"]