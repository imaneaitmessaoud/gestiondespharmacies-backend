#  Gestion des Pharmacies

Ce projet est une application web (et mobile) permettant la gestion complète d’une pharmacie : médicaments, ventes, alertes de stock et péremption, utilisateurs et statistiques.

##  Fonctionnalités

- Authentification sécurisée (JWT)
- Gestion des utilisateurs (Admin, Employés)
- Gestion des médicaments
- Alertes de stock faible et péremption
- Ventes et génération de tickets (simulée)
- Tableau de bord statistique

##  Technologies utilisées

### Backend
- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- MySQL

### Frontend
- React.js
- Axios

### Mobile
- React Native (optionnel)

---

##  Lancer le projet en local

###  Prérequis
- Java 17
- Node.js
- MySQL installé
- Maven

###  Backend (Spring Boot)
```bash
cd backend
mvn clean install
mvn spring-boot:run
## Accès :
http://localhost:8080/api/auth/login

##Frontend (React)

cd frontend-web
npm install
npm start
##Interface : http://localhost:3000

##Authentification
L'authentification se fait via un token JWT stocké dans le localStorage.
Deux rôles sont définis :
ROLE_ADMIN : accès total
ROLE_USER : accès restreint aux ventes et stocks

## Structure du projet

/backend
  ├── controller/
  ├── service/
  ├── model/
  ├── repository/
  └── security/

/frontend-web
  ├── src/
  ├── public/
  └── package.json

##Commandes utiles
Nettoyage et build backend :
mvn clean install
##Lancer frontend :
npm start



##Nom de ton équipe
Imane Aitmessaoud , Kaoutar Belail



