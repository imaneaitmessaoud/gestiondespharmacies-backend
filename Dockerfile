# Utiliser une image de base Java 17
FROM openjdk:17

# Copier ton jar dans l'image
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Commande de démarrage
ENTRYPOINT ["java", "-jar", "app.jar"]
