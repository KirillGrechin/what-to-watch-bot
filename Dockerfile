FROM openjdk:15

ARG JAR_FILE=target/what-to-watch-bot-1.0-SNAPSHOT-jar-with-dependencies.jar
ARG CHATS_FILE=Chats.json
ARG FILMS_FILE=Films.json

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar
COPY ${CHATS_FILE} Chats.json
COPY ${FILMS_FILE} Films.json

ENTRYPOINT ["java", "-jar", "app.jar"]