FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} klinika.jar
ENTRYPOINT ["java","-jar","/klinika.jar"]
EXPOSE 8082:8082