# Construction Workforce Management System

## Project Structure

```text
Midterm-project/
|- backend/                      # Spring Boot backend API
|  |- src/main/java/...
|  |- src/main/resources/application.properties
|  |- src/test/java/...
|  \- pom.xml
|- frontend/                     # React + Vite frontend
|  |- src/
|  |- styles/
|  \- package.json
\- README.md
```

## Prerequisites

- Java 17+
- Maven 3.9+ (or use `backend/mvnw.cmd`)
- Node.js 18+ and npm
- PostgreSQL (default backend DB in `backend/src/main/resources/application.properties`)

## Run Backend

From project root:

```powershell
cd backend
mvn spring-boot:run
```

Backend runs on `http://localhost:8083`.

## Run Frontend

From project root:

```powershell
cd frontend
npm install
npm run dev
```

Frontend runs on `http://localhost:5173`.

## Frontend-Backend Interaction

- Frontend API base URL is configured in `frontend/src/config.js`.
- Default value is `http://localhost:8083`.
- Backend CORS origins are configured in `backend/src/main/resources/application.properties` and `CorsConfig`.

Optional override for frontend API base URL:

```powershell
cd frontend
$env:VITE_API_BASE_URL="http://localhost:8083"
npm run dev
```
