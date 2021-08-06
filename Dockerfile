FROM openjdk:8-jdk-alpine

ARG JAR_FILE=./mongodbJar.jar

RUN mkdir -p /home/java-app

COPY ${JAR_FILE} /home/java-app/app.jar

CMD ["java","-jar","/home/java-app/app.jar"]
