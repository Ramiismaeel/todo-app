# Todo app

![Java](https://img.shields.io/badge/java-25-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-4-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![JUnit 6](https://img.shields.io/badge/JUnit-6-25A162.svg?style=for-the-badge&logo=spring&logoColor=white)



A Spring Boot REST backend for a simple Kanban-style ToDo app. The React frontend (provided by neuefische) is served as static files by the backend, and todos are stored in MongoDB.

## Tech Stack

| Area | Technology |
|---|---|
| Language | Java 25 |
| Framework | Spring Boot 4.1 (Web MVC) |
| Database | MongoDB (Spring Data MongoDB) |
| API docs | springdoc-openapi (Swagger UI) |
| Boilerplate | Lombok |
| Testing | JUnit, MockMvc, embedded MongoDB (Flapdoodle) |
| CI | GitHub Actions (Maven build + tests) |

## Getting Started

### Prerequisites

- JDK 25
- Maven (or the Maven integration in IntelliJ)
- A running MongoDB instance (local or Atlas)

### 1. Add the frontend

1. Download the [frontend](https://github.com/neuefische/java-todo-frontend/releases/download/v1.0.0/frontend.zip).
2. Unzip it and copy its contents into `src/main/resources/static`.

### 2. Configure MongoDB

In `src/main/resources/application.properties`:

```properties
spring.mongodb.uri=YOUR_MONGODB_CONNECT_URL
```

### 3. Run the app

```bash
mvn spring-boot:run
```

Then open:

- Frontend: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui/index.html

## Analysis: What the Frontend Calls

Using the browser's developer tools (Network tab), these requests were found:

| Action in the UI | HTTP method | URL | Request body |
|---|---|---|---|
| Load all todos | `GET` | `/api/todo` | – |
| Add a new todo | `POST` | `/api/todo` | `{ "description": "...", "status": "OPEN" }` |
| Open details / edit page | `GET` | `/api/todo/{id}` | – |
| Save edit / "Advance" button | `PUT` | `/api/todo/{id}` | `{ "id": "...", "description": "...", "status": "..." }` |
| Delete (only in "Done") | `DELETE` | `/api/todo/{id}` | – |

## API Endpoints

Base URL: `/api/todo`

| Method | Endpoint | Description | Response |
|---|---|---|---|
| `GET` | `/api/todo` | Returns all todos | List of todos |
| `POST` | `/api/todo` | Creates a new todo | The saved todo (with generated `id`) |
| `GET` | `/api/todo/{id}` | Returns one todo by id | The todo, or **404** if not found |
| `PUT` | `/api/todo/{id}` | Updates an existing todo | The updated todo, or **404** if not found |
| `DELETE` | `/api/todo/{id}` | Deletes a todo | Empty response, or **404** if not found |

### Todo model

```json
{
  "id": "6720f1c2a1b2c3d4e5f60789",
  "description": "Write README",
  "status": "OPEN"
}
```

### Status enum

| Value | Column in the frontend |
|---|---|
| `OPEN` | Todo |
| `IN_PROGRESS` | Doing |
| `DONE` | Done |

The "Advance" button moves a todo `OPEN → IN_PROGRESS → DONE`.

## Testing

Tests use MockMvc for the controller layer and an embedded MongoDB (Flapdoodle), so no external database is needed.

```bash
mvn verify
```

The embedded MongoDB version is set in `src/test/resources/application.properties`:

```properties
de.flapdoodle.mongodb.embedded.version=7.0.4
```

## Continuous Integration

Every push runs the GitHub Actions workflow `.github/workflows/maven.yml`, which builds the project with JDK 25 and runs all tests with `mvn -B verify`.

## Project Structure

```
src/main/java/recap/todo
├── controller   # REST controller (TodoController)
├── service      # Business logic (TodoService)
├── repository   # MongoDB repository (TodoRepo)
└── model        # Todo, DTOs, Status enum
src/main/resources/static   # React frontend build
```
