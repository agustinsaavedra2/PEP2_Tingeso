FROM openjdk:17
ARG JAR_FILE=target/discount-npeople-service.jar
COPY ${JAR_FILE} discount-npeople-service.jar
ENTRYPOINT ["java", "-jar", "/discount-npeople-service.jar"]