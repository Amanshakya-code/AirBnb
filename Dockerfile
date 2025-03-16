# Use OpenJDK as the base image
FROM openjdk:23-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper and source code
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src/ src/

# Give execute permission to the Maven wrapper
RUN chmod +x mvnw

# Build the application inside the Docker container
RUN ./mvnw clean package -DskipTests

# Copy the built JAR file
RUN cp target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
