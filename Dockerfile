FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} klinika.jar
ENTRYPOINT ["java","-jar","/klinika.jar"]
EXPOSE 8082:8080