# Runtime-only image. The executable jar is built on the CI runner (JVM
# bytecode is platform-independent), so this image just needs a JRE. buildx
# targets linux/arm64 for the Oracle/ARM server — only the base image differs
# per-arch, the copied jar is identical, so there's no emulated compile step.
FROM eclipse-temurin:21-jre

WORKDIR /app

# Built by `./gradlew bootJar` before the image build (see deploy workflow).
COPY build/libs/app.jar app.jar

EXPOSE 8080

# MaxRAMPercentage lets the JVM size the heap from the container's memory limit.
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
