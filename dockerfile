FROM openjdk:17-jdk-slim-buster
# Set the working directory in the container
WORKDIR /app
# Copy the built JAR file from the previous stage to the container
COPY ./target/assist-leave-0.0.1-SNAPSHOT.jar /app
EXPOSE 8020
# Set the command to run the application
CMD ["java", "-jar", "assist-leave-0.0.1-SNAPSHOT.jar"]