FROM openjdk:17

WORKDIR /app


COPY ./target/server-0.0.5-DEV.jar /app/server.jar


EXPOSE 8062


CMD ["java", "-jar", "/app/server.jar"]