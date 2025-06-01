FROM openjdk:17
ARG JAR_FILE=target/discount-freqclients-service.jar
COPY ${JAR_FILE} discount-freqclients-service.jar
ENTRYPOINT ["java", "-jar", "/discount-freqclients-service.jar"]