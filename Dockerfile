
# Use a base image with Java 17
FROM eclipse-temurin:17-jdk

# Set the working directory inside the container
WORKDIR /mortgage

# Copy the built jar file into the container
COPY target/BankApplication-0.0.1-SNAPSHOT.jar mortgage.jar

# Expose the port your app runs on
EXPOSE 8888

# Run the application
ENTRYPOINT ["java", "-jar", "mortgage.jar"]


