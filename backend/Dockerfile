# Offical Image로 자바 베이스 설정
FROM azul/zulu-openjdk:17-latest

# 작업 경로 지정
WORKDIR /app/backend

# JAR 파일 위치 지정
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
