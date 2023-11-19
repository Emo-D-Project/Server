# Use the official OpenJDK base image
FROM adoptopenjdk:17-jdk-hotspot

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle Wrapper files into the container
COPY ./gradlew /app/
COPY ./gradle /app/gradle

# Copy the project files into the container
COPY . /app/

# Grant execute permissions to the Gradle Wrapper
RUN chmod +x ./gradlew

# Build the project
RUN ./gradlew build

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
CMD ["./gradlew", "bootRun"]
