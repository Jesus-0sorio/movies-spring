FROM maven:3.9-amazoncorretto-17

COPY target/movies-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

