FROM openjdk:11-jre
COPY build/libs/bankservice-0.0.1-SNAPSHOT.jar bankservice.jar
ENTRYPOINT ["java", "-jar", "/bankservice.jar"]
