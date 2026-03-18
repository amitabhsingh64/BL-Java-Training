# Fundoo Notes — Backend Reference

**Framework:** Spring Boot 4.0.3
**Language:** Java 21
**Build Tool:** Maven
**Database:** MySQL 8+
**Root Package:** `org.amitabh.fundoobackend`
**Location:** `backend/fundooBackend/`

---

## Directory Structure

```
fundooBackend/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/org/amitabh/fundoobackend/
│   │   │   ├── FundooBackendApplication.java        # Entry point
│   │   │   ├── controller/
│   │   │   │   └── UserController.java              # REST endpoints
│   │   │   ├── dto/
│   │   │   │   ├── RegisterRequest.java
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── LoginResponse.java
│   │   │   │   └── ForgotPasswordRequest.java
│   │   │   ├── model/
│   │   │   │   └── User.java                        # JPA entity
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java
│   │   │   └── service/
│   │   │       ├── UserService.java                 # Interface
│   │   │       └── impl/
│   │   │           └── UserServiceImpl.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/org/amitabh/fundoobackend/
│           └── FundooBackendApplicationTests.java
```

---

## Maven Dependencies (`pom.xml`)

| Dependency | Scope | Purpose |
|---|---|---|
| `spring-boot-starter-data-jpa` | compile | ORM / persistence |
| `spring-boot-starter-webmvc` | compile | REST API / MVC |
| `mysql-connector-j` | runtime | MySQL JDBC driver |
| `lombok` | optional | Boilerplate reduction |
| `spring-boot-starter-test` | test | JUnit / integration tests |

**Build plugins:** `maven-compiler-plugin` (with Lombok annotation processor), `spring-boot-maven-plugin`.

---

## Configuration (`application.properties`)

```properties
spring.application.name=fundooBackend
server.port=8080

# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/fundoo?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=amitabh@128
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

> **Warning:** Credentials are hardcoded. Move to environment variables or a secrets manager before any shared/production deployment.

---

## Entity

### `User` — table `users`

```java
@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;          // ⚠ stored as plaintext — see security notes
}
```

**Generated schema columns:** `id` (VARCHAR 36 PK), `first_name`, `last_name`, `email` (UNIQUE), `password`.

---

## Repository

### `UserRepository extends JpaRepository<User, String>`

Custom query methods:

```java
Optional<User> findByEmail(String email);
boolean existsByEmail(String email);
```

All standard `JpaRepository` methods inherited (`save`, `findById`, `findAll`, `deleteById`, etc.).

---

## DTOs

### Request DTOs

```java
// RegisterRequest
@Data
class RegisterRequest {
    String firstName;
    String lastName;
    String email;
    String password;
}

// LoginRequest
@Data
class LoginRequest {
    String email;
    String password;
}

// ForgotPasswordRequest
@Data
class ForgotPasswordRequest {
    String email;
    String newPassword;
}
```

### Response DTO

```java
// LoginResponse — used for both register and login responses
@Data @AllArgsConstructor
class LoginResponse {
    String id;
    String firstName;
    String lastName;
    String email;
    // password is intentionally excluded
}
```

---

## Service Layer

### Interface `UserService`

```java
LoginResponse register(RegisterRequest request);
LoginResponse login(LoginRequest request);
void forgotPassword(ForgotPasswordRequest request);
```

### Implementation `UserServiceImpl`

#### `register`
1. `existsByEmail(email)` → if true, throw `RuntimeException("Email already registered")`
2. Build `User` from request fields (password stored as-is — no hashing)
3. `userRepository.save(user)`
4. Return `LoginResponse(id, firstName, lastName, email)`

#### `login`
1. `findByEmail(email)` → if empty, throw `RuntimeException("Invalid email or password")`
2. `user.getPassword().equals(request.getPassword())` → if false, throw same exception
3. Return `LoginResponse`

#### `forgotPassword`
1. `findByEmail(email)` → if empty, throw `RuntimeException("No account found with this email")`
2. `user.setPassword(request.getNewPassword())` (plaintext)
3. `userRepository.save(user)`

---

## REST API

**Base path:** `/api/users`
**CORS allowed origin:** `http://localhost:5173` (React dev server)

---

### `POST /api/users/register`

**Request:**
```json
{
  "firstName": "Amitabh",
  "lastName": "Singh",
  "email": "user@example.com",
  "password": "secret123"
}
```

**Success `201 Created`:**
```json
{
  "id": "f5c18ad4-e274-4a05-b93b-dc78b86bc1ca",
  "firstName": "Amitabh",
  "lastName": "Singh",
  "email": "user@example.com"
}
```

**Error `400 Bad Request`** (duplicate email):
```json
{ "message": "Email already registered" }
```

---

### `POST /api/users/login`

**Request:**
```json
{
  "email": "user@example.com",
  "password": "secret123"
}
```

**Success `200 OK`:**
```json
{
  "id": "f5c18ad4-e274-4a05-b93b-dc78b86bc1ca",
  "firstName": "Amitabh",
  "lastName": "Singh",
  "email": "user@example.com"
}
```

**Error `401 Unauthorized`:**
```json
{ "message": "Invalid email or password" }
```

---

### `POST /api/users/forgot-password`

**Request:**
```json
{
  "email": "user@example.com",
  "newPassword": "newSecret123"
}
```

**Success `200 OK`:**
```
"Password updated successfully"
```

**Error `400 Bad Request`:**
```json
{ "message": "No account found with this email" }
```

---

## Error Handling Pattern

Currently handled inline in the controller with try-catch:

```java
try {
    return ResponseEntity.status(201).body(userService.register(request));
} catch (RuntimeException e) {
    return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
}
```

No global `@ControllerAdvice` exists yet. All service exceptions are `RuntimeException`.

---

## Running the Application

**Prerequisites:** MySQL running on port 3306 (database `fundoo` is auto-created).

```bash
# From backend/fundooBackend/
./mvnw spring-boot:run

# Or build and run jar
./mvnw clean package -DskipTests
java -jar target/fundooBackend-0.0.1-SNAPSHOT.jar
```

---

## What Is Not Implemented Yet

The following features are expected by the frontend (see `frontend.md`) but do not exist in this backend:

### Notes API (needed by frontend)
The frontend currently uses json-server on port 3000 for notes. These endpoints need to be built:

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/notes` | Fetch all notes (filter by `userId`, `isTrash`, `isArchive`) |
| `POST` | `/api/notes` | Create a note |
| `PATCH` | `/api/notes/{id}` | Partial update (any subset of fields) |
| `DELETE` | `/api/notes/{id}` | Hard delete (permanent) |

**Note entity fields required:**
```java
String id;          // UUID PK
String userId;      // FK to User.id (plain string, not a JPA relation)
String title;
String text;
String bgColor;     // hex color string, e.g. "#faafa8"
String bgImage;     // asset path or empty string
boolean isTrash;
boolean isArchive;
```

### Security Gaps (fix before production)
- **Plaintext passwords** — wrap with `BCryptPasswordEncoder` in `UserServiceImpl`.
- **No JWT / session tokens** — login returns a user object stored in localStorage with no server-side session. Add Spring Security + JWT to issue/validate tokens; frontend must attach `Authorization: Bearer <token>` header to note requests.
- **No input validation** — add `@Valid` + Bean Validation (`@NotBlank`, `@Email`, `@Size`) to request DTOs and `@Validated` on the controller.
- **Credentials in properties file** — externalize via environment variables or Spring Cloud Config.
- **`ddl-auto=update`** — use `validate` or a migration tool (Flyway/Liquibase) for any shared environment.

### Other Missing Pieces
- No global exception handler (`@ControllerAdvice`) — error shape is inconsistent (some return `Map`, some return plain strings).
- No logging (SLF4J / Logback not configured beyond defaults).
- No pagination for notes endpoints.
- Only one test (`contextLoads`) — no unit or integration tests for service/controller layers.

---

## Recommended Next Steps

1. **Add BCrypt password hashing**
   ```java
   // Bean in main class or @Configuration
   @Bean
   public BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

   // In UserServiceImpl.register()
   user.setPassword(passwordEncoder.encode(request.getPassword()));

   // In UserServiceImpl.login()
   passwordEncoder.matches(request.getPassword(), user.getPassword())
   ```

2. **Build the Note entity + CRUD** (see fields above), create `NoteRepository`, `NoteService`, `NoteController` at `/api/notes`.

3. **Add `@ControllerAdvice`** for uniform error responses across all endpoints.

4. **Add Bean Validation** to all DTOs (`@NotBlank`, `@Email`, `@Size(min=8)` on password).

5. **Add CORS for notes controller** (same `@CrossOrigin(origins = "http://localhost:5173")` pattern, or move to global `WebMvcConfigurer`).

6. **Update frontend** `src/services/notesService.js` base URL from `http://localhost:3000/notes` → `http://localhost:8080/api/notes` after notes API is ready.
