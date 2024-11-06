# Stage 1: Build the application 
FROM maven:3.8.8-eclipse-temurin-21 AS build 

# Set the working directory 
WORKDIR /app  

# Copy Maven's configuration and install dependencies 
COPY pom.xml . 
RUN mvn dependency:go-offline 

# Copy the rest of the application source code 
COPY src ./src 

# Build the application (creates a JAR file in the /target directory) 
RUN mvn clean package -DskipTests 

# Stage 2: Run the application 
FROM eclipse-temurin:21-jdk-jammy

# Set the working directory 
WORKDIR /app 

# Copy the JAR file from the previous stage 
COPY --from=build /app/target/*.jar app.jar 

# Expose the port that the app runs on 
EXPOSE 8080 

# Run the Spring Boot application 
ENTRYPOINT ["java", "-jar", "app.jar"]