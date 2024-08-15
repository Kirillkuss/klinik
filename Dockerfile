FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} authklinik.jar
ENTRYPOINT ["java","-jar","/authklinik.jar"]
EXPOSE 8082:8082