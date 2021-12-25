FROM maven:3.6.3-jdk-11

COPY src /opt/app/src
COPY pom.xml Chats.json Films.json ./opt/app/

WORKDIR /opt/app

RUN mvn -f pom.xml clean package
ENTRYPOINT ["java", "-jar", "target/what-to-watch-bot-1.0-SNAPSHOT-jar-with-dependencies.jar"]