# Mortgage Backend API

Java Spring Boot REST API to manage mortgage interest rates and perform mortgage feasibility checks.

# Features

GET /api/interest-rates: Retrieve current in-memory interest rates.

POST /api/mortgage-check: Submit mortgage parameters to check feasibility and calculate monthly cost.
Swagger UI for interactive API documentation.

# Prerequisites

Java 17+

Maven 3.8+
# Download the code from the Github Repo
https://github.com/manu2716/BankApplication

# Build the project

mvn clean install

# Run the application

mvn spring-boot:run

The application will start on http://localhost:8888.

# Run the application using Docker
Go to the root of the project in the terminal and run the follwing commands.

docker build -t bank-application .

docker run bank-application:latest

# API Documentation

http://localhost:8888/swagger-ui/index.html

The PostMan collection file is located in the docs/ folder at the root of the project.

# Technologies Used

Java 17

Spring Boot

Spring Web

Springdoc OpenAPI (Swagger)

Maven

