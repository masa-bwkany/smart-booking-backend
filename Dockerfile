# ---------- Build stage ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
# Ensure gradlew is executable (in case git lost the bit)
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar -x test

# ---------- Run stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app
# copy the built jar
COPY --from=build /app/build/libs/*.jar app.jar

# optional JVM opts; Render will inject PORT
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080
CMD ["sh","-c","java $JAVA_OPTS -jar app.jar --server.port=${PORT:-8080}"]