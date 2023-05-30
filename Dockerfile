FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Klinik.jar
ENTRYPOINT ["java","-jar","/Klinik.jar"]
EXPOSE 8082:8081