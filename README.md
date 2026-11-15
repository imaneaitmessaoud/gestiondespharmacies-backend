# Pharmacy Management Backend

A REST API backend for a pharmacy management application, built with Java and Spring Boot.

## Technology stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.4
- **Web layer:** Spring Web
- **Persistence:** Spring Data JPA
- **Database driver:** MySQL Connector/J
- **Security dependencies:** Spring Security and JSON Web Token (JWT) libraries
- **Build tool:** Maven
- **Testing:** Spring Boot Test

## Architecture

This repository contains the backend API. The companion React frontend is available here:

[Pharmacy Management Frontend](https://github.com/imaneaitmessaoud/Front-gestion-despharmacies)

## Getting started

### Prerequisites

- JDK 17
- Maven
- MySQL Server

### Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/imaneaitmessaoud/gestiondespharmacies-backend.git
   cd gestiondespharmacies-backend
   ```

2. Create a MySQL database for the application.

3. Configure the database connection and application-specific settings in your local Spring Boot configuration. Keep credentials and secrets outside version control.

4. Build and run the application:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Tests

Run the test suite with:

```bash
mvn test
```

## Security note

The project includes Spring Security and JWT dependencies. Configure signing keys and database credentials securely using environment-specific configuration. Never commit secrets to the repository.

## Author

**Imane Ait Messaoud**  
[GitHub](https://github.com/imaneaitmessaoud) · [LinkedIn](https://www.linkedin.com/in/imane-aitmessaoud-756666391/)
