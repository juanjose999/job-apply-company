FROM amazoncorretto:21-alpine-jdk
LABEL authors="juancho"

COPY target/jobify-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]



