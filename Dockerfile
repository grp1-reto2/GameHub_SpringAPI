# ETAPA 1: Construcción (Usamos Maven con Alpine)
# Usamos una imagen que ya tiene Maven y JDK para compilar
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Empaquetamos saltando tests para ir rápido en el despliegue
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución (La imagen final que usará Alpine JRE)
# Usamos JRE (Java Runtime) en vez de JDK para ahorrar más espacio
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copiamos solo el .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Configuración de memoria para no saturar la EC2 (IMPORTANTE)
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]