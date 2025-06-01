FROM openjdk:17
ARG JAR_FILE=target/discount-specialdays-service.jar
COPY ${JAR_FILE} discount-specialdays-service.jar
ENTRYPOINT ["java", "-jar", "/discount-specialdays-service.jar"]