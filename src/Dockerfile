FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:17-jdk-slim

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod
ENV DATABASE_URL=jdbc:postgresql://dpg-cqhilu08fa8c73bsrt10-a:5432/deploy_imoobify
ENV DATABASE_USERNAME=admin
ENV DATABASE_PASSWORD=ukthXs7yFueEQmLL4yngWEMJTpJzHIgZ

COPY --from=build /target/Pet-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]