# Multi-stage build to ensure Gradle and Python are available without local installation

# Ref: https://hub.docker.com/_/gradle
FROM gradle:8.5-jdk17 AS builder
WORKDIR /home/gradle/project

# Copy Spring Boot project files
# We assume the build context is the repository root
COPY springboot-backend/ .

# Build the JAR file
RUN gradle bootJar --no-daemon

# -----------------------------------------------------------------------------

# Ref: https://hub.docker.com/_/openjdk
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Install Python 3
RUN apt-get update && \
    apt-get install -y python3 && \
    rm -rf /var/lib/apt/lists/*

# Copy the JAR file from the builder stage
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

# Copy Python scripts
# The Spring Boot application expects python scripts to be in "../python-scripts" relative to the working directory.
# Since WORKDIR is /app, "../python-scripts" resolves to /python-scripts.
COPY python-scripts/ /python-scripts/

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
