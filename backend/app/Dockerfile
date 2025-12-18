# ============================================
# TODO (Future Optimizations)
# - Switch to distroless or smaller JRE
# - Enable remote debugging (port 5005)
# - Multi-arch builds
# - Add HEALTHCHECK
# ============================================

# Build stage: maven
FROM maven:3.8-openjdk-18-slim AS build-maven
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests -X

# Runtime stage
FROM ubuntu:22.04
ENV DEBIAN_FRONTEND=noninteractive
WORKDIR /app
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    rm -rf /var/lib/apt/lists/*
COPY --from=build-maven /app/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]