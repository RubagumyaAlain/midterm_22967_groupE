# Backend (Spring Boot)

## Prerequisites

- Java 17+
- Maven 3.9+ (or use Maven wrapper `mvnw.cmd`)
- PostgreSQL (default DB values are configured in `src/main/resources/application.properties`)

## Run

```powershell
mvn spring-boot:run
```

Default backend URL: `http://localhost:8083`

## Test

```powershell
mvn test
```

## Configuration

Main backend config file:

- `src/main/resources/application.properties`

Important values:

- `server.port=8083`
- `spring.datasource.*` for database connection
- `app.cors.allowed-origins` for frontend origins
