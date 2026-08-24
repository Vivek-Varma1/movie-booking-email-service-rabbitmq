FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Install Maven package directly inside the container to avoid network downloads via ./mvnw
RUN apt-get update && apt-get install -y maven

COPY . .

# Build using system-installed maven instead of wrapper script
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]