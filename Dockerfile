FROM openjdk:13-alpine
ARG JAR_FILE=target/shopping-cart-0.0.1-SNAPSHOT.jar
WORKDIR /opt/myshoppingapp
COPY ${JAR_FILE} app.jar
COPY initDB.sql /docker-entrypoint-initdb.d/
ENTRYPOINT ["java","-jar","/app.jar"]