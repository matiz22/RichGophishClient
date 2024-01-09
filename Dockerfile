FROM gradle:8.4-jdk17 AS build

WORKDIR /
COPY ./server server
COPY ./gradlew gradlew
COPY ./gradle gradle
COPY ./build.gradle.kts build.gradle.kts
COPY ./settings.gradle.kts settings.gradle.kts
COPY ./shared shared


RUN bash gradlew :server:build

EXPOSE 8080

CMD ["java", "-cp", "/server/build/libs/server-all.jar", "com.matiz22.richgophishclient.ApplicationKt"]