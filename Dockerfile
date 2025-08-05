FROM gradle:8.7.0-jdk17-alpine AS builder
WORKDIR /app

COPY build.gradle settings.gradle ./
COPY loan-service/build.gradle ./loan-service/
COPY loan-processor/build.gradle ./loan-processor/

RUN gradle -b build.gradle --no-daemon --parallel --console=plain build -x test || true

COPY . .

RUN gradle :loan-service:bootJar :loan-processor:bootJar --no-daemon

FROM eclipse-temurin:17-jdk-alpine  AS loan-service
WORKDIR /app
COPY --from=builder /app/loan-service/build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]

FROM eclipse-temurin:17-jdk-alpine  AS loan-processor
WORKDIR /app
COPY --from=builder /app/loan-processor/build/libs/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","app.jar"]