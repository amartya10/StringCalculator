FROM maven:3.8-openjdk-8
WORKDIR /app
COPY src /app/src
COPY pom.xml /app


