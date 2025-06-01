FROM openjdk:17
ARG JAR_FILE=target/weekly-rack-service.jar
COPY ${JAR_FILE} weekly-rack-service.jar
ENTRYPOINT ["java", "-jar", "/weekly-rack-service.jar"]