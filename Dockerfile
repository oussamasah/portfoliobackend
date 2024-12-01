# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file to the container
COPY target/OussamaApplication.jar app.jar

# Expose the app's default port (8080)
EXPOSE 8002

# Run the application
CMD ["java", "-jar", "app.jar"]
