# Utiliser l'image de base de Java 17
FROM openjdk:17-jdk-slim as build

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier jar de l'application dans l'image
COPY target/myapp.jar /app/myapp.jar

# Exposer le port sur lequel Spring Boot s'exécute (par défaut 8080)
EXPOSE 8080

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "myapp.jar"]
