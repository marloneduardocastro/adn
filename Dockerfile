FROM openjdk:13-jdk-slim

EXPOSE 8080

ADD  ./adn-0.0.1-SNAPSHOT.jar app.jar
ADD  ./application.yml application.yml

ENTRYPOINT ["java", "-jar","app.jar"]
