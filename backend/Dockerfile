# Offical Image로 자바 베이스 설정
FROM gradle:7.6.1-jdk17 AS build

# 작업 경로 지정
WORKDIR /app/backend

COPY build.gradle .
COPY src ./src

COPY gradle ./gradle
COPY gradlew ./gradlew

RUN ./gradlew build

FROM azul/zulu-openjdk:17-latest

WORKDIR /app/backend

# JAR 파일 위치 지정
COPY --from=build /app/backend/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
