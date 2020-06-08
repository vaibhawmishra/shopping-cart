# Build jar
FROM maven:3.6.3-openjdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Run jar
FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/shopping-cart-0.0.1-SNAPSHOT.jar
WORKDIR /opt/myshoppingapp
COPY ${JAR_FILE} app.jar

# run init database scripts
COPY initDB.sql /docker-entrypoint-initdb.d/
ENTRYPOINT ["java","-jar","/app.jar"]
