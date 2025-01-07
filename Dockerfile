# Use an image with Gradle and Java pre-installed
FROM gradle:8.11.1-jdk17 AS builder
# Set the working directory inside the container
WORKDIR /app

# Copy Gradle wrapper and other necessary files first for caching dependencies
COPY gradle/ gradle/
COPY gradlew build.gradle settings.gradle ./
# Copy the rest of the application source code
COPY src/ src/

# Cache dependencies
RUN ./gradlew dependencies


# Run the clean and build tasks to create the JAR
RUN ./gradlew clean build --no-daemon

# List the contents of the build/libs directory for debugging
RUN ls -al /app

# Start with OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim


# Copy the application JAR file into the container
COPY --from=builder /app/build/libs/recieptprocessor-0.0.1-SNAPSHOT.jar /app/receipt-processor.jar

# Expose the application port (if your app runs on a specific port)
EXPOSE 8080

# Define the entrypoint to run the application
ENTRYPOINT ["java", "-jar", "/app/receipt-processor.jar"]
