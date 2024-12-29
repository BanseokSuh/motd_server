FROM openjdk:21

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENV SPRING_PROFILE prod

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE}", "-jar", "/app.jar"]