FROM openjdk:17-jdk

WORKDIR /app

COPY target/pruebaMicroserviciosUno-0.0.1-SNAPSHOT.jar /app/pruebaMicroserviciosUno-0.0.1-SNAPSHOT.jar
COPY src/main/resources/application.properties /app/application.properties

EXPOSE 8000

CMD ["java", "-jar", "pruebaMicroserviciosUno-0.0.1-SNAPSHOT.jar"]