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

frontend-gestion-des-pharmacies/
├── public/
├── src/
│   ├── assets/               # Images, logos, styles
│   ├── components/           # Composants partagés (Sidebar, Navbar, Header...)
│   ├── context/              # Authentification (AuthContext.js)
│   ├── layouts/              # Layouts Auth & Admin
│   ├── views/                # Pages principales : Dashboard, Médicaments, Ventes...
│   ├── routes.js             # Configuration des routes
│   ├── api.js                # Configuration Axios
│   └── App.js / index.js     # Point d’entrée de l’application


##Commandes utiles
 Installation & Lancement

### Prérequis

- Node.js (v16+ recommandé)
- npm ou yarn
- Backend API opérationnel (ex. http://localhost:8081/api)

### Démarrage en local

bash
git clone https://github.com/[ton-repo](https://github.com/imaneaitmessaoud/gestiondespharmacies-backend)/frontend-gestion-des-pharmacies.git
cd frontend-gestion-des-pharmacies
npm install
npm start


Par défaut, l'app s'exécute sur : [http://localhost:3000](http://localhost:3000)


##  Authentification

- Le token JWT est stocké dans `localStorage` (`accessToken`, `email`)
- Les routes privées sont protégées avec `PrivateRoute`
- Un utilisateur connecté reste authentifié même après actualisation

---

##  Test API (Postman)

Assurez-vous que votre API backend tourne sur `http://localhost:8081/api`. Voici quelques requêtes types pour tester chaque ressource dans Postman :

###  Authentification

- **POST** `/auth/login`
```json
{
  "email": "admin@pharma.com",
  "motDePasse": "123456"
}
```

###  Utilisateurs

- **GET** `/utilisateurs`
- **GET** `/utilisateurs/{id}`
- **POST** `/utilisateurs`
- **PUT** `/utilisateurs/{id}`
- **DELETE** `/utilisateurs/{id}`

###  Médicaments

- **GET** `/medicaments`
- **POST** `/medicaments`
```json
{
  "nom": "Doliprane",
  "quantiteStock": 50,
  "prix": 5.0,
  "dateExpiration": "2025-10-01",
  "categorieId": 1
}
```

###  Ventes

- **GET** `/ventes`  ventedto-> /ventes/dto   
- **GET** `/ventes/{id}`  ventedto -> /ventes/dto/{id}
- **POST** `/ventes`
```json
{
  "dateVente": "2025-05-13T15:00",
  "utilisateurId": 2,
  "lignes": [
    { "medicamentId": 1, "quantite": 3 }
  ]
}
```
- **PUT** `/ventes/{id}`
- **DELETE** `/ventes/{id}`
  ###  Verifier Alerte
/api/alertes/verifier 
POST 
Json {}
  
###  Alertes

- **GET** `/alertes`
- **PUT** `/alertes/{id}/lire`

###  Catégories

- **GET** `/categories`
- **POST** `/categories`
```json
{
  "nom": "Antibiotique",
  "description": "Contre les infections"
}
```

---

##  Notes supplémentaires

- Les tokens JWT doivent être ajoutés dans Postman via l’en-tête :
```
Authorization: Bearer <accessToken>
```
- Vérifiez que `api.js` pointe bien vers `http://localhost:8081/api`

---

## ✨ Auteur
##Nom de l'équipe
Imane Aitmessaoud , Kaoutar Belail



