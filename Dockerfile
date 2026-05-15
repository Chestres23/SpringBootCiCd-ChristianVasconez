# =========================
# Etapa 1: Build
# =========================
FROM gradle:8.14.3-jdk17 AS builder

WORKDIR /app

COPY . .

RUN gradle clean bootJar --no-daemon

## Forzar error intencional en la etapa de build
#RUN exit 1

# =========================
# Etapa 2: Run
# =========================
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]