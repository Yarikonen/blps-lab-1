FROM openjdk:17-oracle
ARG JAR_FILE=consumer/target/*.jar
COPY ${JAR_FILE} consumer.jar
EXPOSE 8081
ENTRYPOINT ["java", "-Dspring.profiles.active=local", "-jar", "/consumer.jar"]