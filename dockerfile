# Utiliser l'image de base de Java 17
FROM openjdk:17-jdk-slim as build

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier jar de l'application dans l'image
ADD target/tp-foyer-5.0.0.jar tp-foyer-5.0.0.jar

# Exposer le port 8081 pour Spring Boot (si vous changez le port dans le fichier application.properties)
EXPOSE 8089

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "myapp.jar"]
