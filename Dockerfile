FROM alpine/git as clone
WORKDIR /app
RUN git clone https://github.com/vaibhawmishra/shopping-cart.git

FROM maven:3.5-jdk-8-alpine as build
WORKDIR /app
COPY --from=clone /app/shopping-cart /app
RUN mvn install

FROM mysql:5.6 as mysql
WORKDIR /app
ADD initDB.sql /docker-entrypoint-initdb.d

FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=build /app/target/shopping-cart-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java -jar shopping-cart-0.0.1-SNAPSHOT.jar"]
