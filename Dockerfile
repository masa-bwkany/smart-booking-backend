# ---- Build stage ----
FROM gradle:8.9-jdk21-alpine AS build
WORKDIR /home/gradle/app
COPY . .
RUN gradle clean bootJar -x test

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /home/gradle/app/build/libs/*.jar app.jar
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"
EXPOSE 8080
# Render sets PORT env var; pass it through to Spring
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar --server.port=${PORT:-8080}"]