FROM openjdk:17
ARG JAR_FILE=target/booking-voucher-service.jar
COPY ${JAR_FILE} booking-voucher-service.jar
ENTRYPOINT ["java", "-jar", "/booking-voucher-service.jar"]