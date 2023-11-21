# Use the official OpenJDK base image
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy your application files to the container
COPY build/libs/*.jar app.jar

# Your additional Dockerfile configuration goes here
ENTRYPOINT ["java","-jar","/app.jar"]
 