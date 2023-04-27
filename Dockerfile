FROM openjdk:17-jdk-bullseye

WORKDIR /app

COPY .gradle ./.gradle
COPY gradlew build.gradle ./
COPY gradle ./gradle
COPY lib ./lib
COPY src ./src

RUN sed -i 's\localhost\mysql\g' src/main/resources/application.properties

RUN ./gradlew

CMD ["./gradlew", "bootRun"]
