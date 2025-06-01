FROM openjdk:17
ARG JAR_FILE=target/booking-rate-service.jar
COPY ${JAR_FILE} booking-rate-service.jar
ENTRYPOINT ["java", "-jar", "/booking-rate-service.jar"]