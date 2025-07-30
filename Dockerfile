FROM gradle:8.7.0-jdk17-alpine AS builder
WORKDIR /app

COPY build.gradle settings.gradle ./
COPY loan-service/build.gradle ./loan-service/

RUN gradle -b build.gradle --no-daemon --parallel --console=plain build -x test || true

COPY . .

RUN gradle :loan-service:bootJar --no-daemon

FROM eclipse-temurin:17-jdk-alpine  AS loan-service
WORKDIR /app
COPY --from=builder /app/loan-service/build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]