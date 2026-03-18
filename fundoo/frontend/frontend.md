# Fundoo Notes — Frontend Reference

**Stack:** React 19 + Vite + MUI v7 + Axios + React Router v7
**Package Manager:** Bun (or npm)
**Location:** `frontend/notesApp/notesApp/`

---

## Directory Structure

```
notesApp/
├── index.html
├── package.json
├── vite.config.js
├── db.json                         # json-server mock database (remove after Spring Boot integration)
├── src/
│   ├── main.jsx                    # React entry point
│   ├── App.jsx                     # Root router config
│   ├── RoutingModules.jsx
│   ├── services/
│   │   ├── authService.js          # Auth helpers (localStorage)
│   │   └── notesService.js         # Notes CRUD (Axios calls to backend)
│   ├── routing/
│   │   ├── ProtectedRoutes.jsx     # Redirect to /login if not authenticated
│   │   └── AuthRoutes.jsx          # Redirect to /dashboard if already logged in
│   ├── pages/
│   │   ├── signUp/SignUp.jsx
│   │   ├── signIn/SignIn.jsx
│   │   ├── forgotPassword/ForgotPassword.jsx
│   │   ├── dashboard/dashboard.jsx
│   │   ├── archive/archive.jsx
│   │   └── trash/trash.jsx
│   └── components/
│       ├── headerComponent/
│       ├── sideBar/
│       ├── addNotesBlock/
│       ├── noteCard/
│       ├── colorPallet/
│       ├── icon/
│       └── accountMenuCard/
```

---

## Runtime Ports

| Service            | Port   | URL                        |
|--------------------|--------|----------------------------|
| React (Vite dev)   | 5173   | `http://localhost:5173`    |
| Spring Boot        | 8080   | `http://localhost:8080`    |
| JSON Server (mock) | 3000   | `http://localhost:3000`    |

> All API URLs are **hardcoded** — no `.env` file. Update them after integration (see [Migration Steps](#migration-to-spring-boot-notes-api)).

---

## Spring Boot API Contract

### Authentication — `http://localhost:8080`

#### `POST /api/users/register`

**Request:**
```json
{
  "firstName": "Amitabh",
  "lastName": "Singh",
  "email": "user@example.com",
  "password": "secret123"
}
```

**Success Response (`200` or `201`):** User object
```json
{
  "id": "<uuid>",
  "firstName": "Amitabh",
  "lastName": "Singh",
  "email": "user@example.com"
}
```

**Error Response (`400`):** Duplicate email
```json
{ "message": "Email already exists" }
```

Frontend behaviour on error: displays `alert(error.response.data.message || "Registration failed")` and stays on the signup page.

---

#### `POST /api/users/login`

**Request:**
```json
{
  "email": "user@example.com",
  "password": "secret123"
}
```

**Success Response (`200`):** User object (same shape as register response — **do NOT include password**)
```json
{
  "id": "<uuid>",
  "firstName": "Amitabh",
  "lastName": "Singh",
  "email": "user@example.com"
}
```

The entire response object is saved to `localStorage` under the key `"user"`.

**Error Response (`401`):**
```json
{ "message": "Invalid credentials" }
```

Frontend behaviour: `alert(error.response.data.message || "Login failed")`.

---

#### `POST /api/users/forgot-password`

**Request:**
```json
{
  "email": "user@example.com",
  "newPassword": "newSecret123"
}
```

**Success Response (`200`):** Any success body or status code is accepted.

**Error Response (`400`):** Email not found
```json
{ "message": "Email not found" }
```

---

### Notes API — currently `http://localhost:3000` (json-server)

> After Spring Boot integration, update `src/services/notesService.js` to point to `http://localhost:8080/api/notes`.

#### `GET /notes`

Returns all notes. Frontend filters client-side by `userId` + `isTrash` + `isArchive`.
If you implement server-side filtering, the frontend must be updated too.

**Response:**
```json
[
  {
    "id": "2",
    "userId": "f5c18ad4-e274-4a05-b93b-dc78b86bc1ca",
    "title": "Grocery list",
    "text": "Milk, eggs, bread",
    "bgColor": "#faafa8",
    "bgImage": "/src/assets/colorPalletBgImg/food_light_thumb_0615.svg",
    "isTrash": false,
    "isArchive": false
  }
]
```

#### `POST /notes`

**Request Body:**
```json
{
  "userId": "<uuid>",
  "title": "My Note",
  "text": "Note content",
  "bgColor": "#ffffff",
  "bgImage": "",
  "isTrash": false,
  "isArchive": false
}
```

**Response:** Created note with `id`.

#### `PATCH /notes/:id`

Partial update — any subset of fields.

**Example payloads:**
```json
{ "isTrash": true }
{ "isArchive": true }
{ "bgColor": "#e2f6d3" }
{ "title": "Updated title", "text": "Updated body" }
```

#### `DELETE /notes/:id`

Permanent deletion (hard delete). Used in Trash page ("Delete Forever").
No request body. Expects `200` or `204`.

---

## Data Models

### User (stored in `localStorage["user"]`)
```typescript
{
  id: string          // UUID — used as userId FK on notes
  firstName: string
  lastName: string
  email: string
  // password is NEVER stored on the client
}
```

### Note
```typescript
{
  id: string          // UUID
  userId: string      // FK to User.id
  title: string
  text: string
  bgColor: string     // Hex, default "#ffffff"
  bgImage: string     // Path or empty string
  isTrash: boolean    // Soft-delete flag
  isArchive: boolean  // Archive flag
}
```

---

## Note Filtering Logic

All filtering is done client-side after fetching all notes:

| Page      | Filter condition                        |
|-----------|-----------------------------------------|
| Dashboard | `!note.isTrash && !note.isArchive`      |
| Archive   | `note.isArchive && !note.isTrash`       |
| Trash     | `note.isTrash`                          |

> When migrating to Spring Boot, consider adding query params (`?isTrash=false&isArchive=false`) to shift this filtering server-side.

---

## Authentication Flow

1. User logs in → Spring Boot validates credentials → returns User object.
2. Frontend saves the object to `localStorage["user"]`.
3. `authService.isAuthenticated()` checks `!!localStorage.getItem("user")`.
4. `ProtectedRoutes` redirects to `/login` if `isAuthenticated()` returns false.
5. `AuthRoutes` redirects authenticated users away from `/login`, `/signup`, `/forgot-password`.
6. Logout clears `localStorage["user"]` and navigates to `/login`.

**Note:** There is no JWT/token-based auth yet. The backend session is **stateless from the frontend's perspective** — Spring Boot must allow CORS for `http://localhost:5173`.

---

## CORS Configuration Required

Add this to your Spring Boot app:

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
```

---

## Client-Side Validation Rules

| Field          | Rule                                         |
|----------------|----------------------------------------------|
| firstName      | Min 2 alphabetic characters                  |
| lastName       | Min 2 alphabetic characters                  |
| email (signup) | Standard email regex                         |
| email (login)  | Must contain `@`                             |
| password       | Min 8 characters                             |
| confirmPassword| Must match password                          |

Validation is enforced **before** the API call, so the backend can trust that these fields meet minimum criteria — but server-side validation is still recommended.

---

## Color Palette Constants

### Background Colors (12 options)
```
#ffffff  (none / white — default)
#faafa8  #f39f76  #fff8b8  #e2f6d3  #b4ddd3
#d4e4ed  #aeccdc  #d3bfdb  #f6e2dd  #e9e3d4  #efeff1
```

### Background Images (8 options)
Stored at `src/assets/colorPalletBgImg/`:
- `grocery_light_thumb_0615.svg`
- `food_light_thumb_0615.svg`
- `travel_light_thumb_0615.svg`
- `places_light_thumb_0615.svg`
- `video_light_thumb_0615.svg`
- `celebration_light_thumb_0615.svg`
- `recipe_light_thumb_0615.svg`

`bgImage` is stored as the full asset path string. When migrating, decide whether to store the path or an enum key.

---

## Routing Table

| Path              | Component       | Auth Guard      |
|-------------------|-----------------|-----------------|
| `/signup`         | SignUp          | AuthRoute       |
| `/login`          | SignIn          | AuthRoute       |
| `/forgot-password`| ForgotPassword  | AuthRoute       |
| `/`               | → `/dashboard`  | ProtectedRoute  |
| `/dashboard`      | Dashboard       | ProtectedRoute  |
| `/trash`          | Trash           | ProtectedRoute  |
| `/archive`        | Archive         | ProtectedRoute  |
| `/reminders`      | Dashboard (stub)| ProtectedRoute  |
| `/labels`         | Dashboard (stub)| ProtectedRoute  |

---

## Service Layer — Files to Update for Integration

### `src/services/notesService.js`
```javascript
// Current (json-server mock):
const BASE_URL = "http://localhost:3000/notes";

// Change to:
const BASE_URL = "http://localhost:8080/api/notes";
```

Methods:
```javascript
getNotes()              // GET  /api/notes
createNote(noteData)    // POST /api/notes
updateNote(id, data)    // PATCH /api/notes/:id
deleteNote(id)          // DELETE /api/notes/:id
```

### `src/pages/signUp/SignUp.jsx`
```javascript
// Already points to:
axios.post("http://localhost:8080/api/users/register", payload)
```

### `src/pages/signIn/SignIn.jsx`
```javascript
axios.post("http://localhost:8080/api/users/login", payload)
```

### `src/pages/forgotPassword/ForgotPassword.jsx`
```javascript
axios.post("http://localhost:8080/api/users/forgot-password", payload)
```

---

## Migration to Spring Boot Notes API

Steps to move from json-server to Spring Boot:

1. Implement the four notes endpoints in Spring Boot: `GET /api/notes`, `POST /api/notes`, `PATCH /api/notes/{id}`, `DELETE /api/notes/{id}`.
2. In `src/services/notesService.js`, change `BASE_URL` from `http://localhost:3000/notes` to `http://localhost:8080/api/notes`.
3. Ensure the `PATCH` endpoint accepts partial updates.
4. The `userId` field must be stored in the `Note` entity as a foreign key to `User`.
5. (Optional) Add `?isTrash=false&isArchive=false&userId=<id>` query param support to filter server-side instead of client-side.
6. Remove `json-server` and `db.json` from the project.

---

## Suggested Spring Boot Entity: Note

```java
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userId;      // FK — not a JPA relation (kept as plain string to match frontend)
    private String title;
    private String text;
    private String bgColor;     // hex string
    private String bgImage;     // asset path or empty string
    private boolean isTrash;
    private boolean isArchive;
}
```

---

## Suggested Spring Boot Entity: User

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;    // store BCrypt hash
}
```

> The frontend never receives `password` in any response — ensure your DTO/response objects exclude it.

---

## Development Commands

```bash
# Install dependencies
bun install       # or: npm install

# Start dev server (port 5173)
bun run dev       # or: npm run dev

# Start json-server mock backend (port 3000) — temporary
npx json-server --watch db.json --port 3000

# Build for production
bun run build
```

---

## Known Gaps / TODOs for Backend

- No token-based auth (JWT). Currently just stores user object in localStorage — anyone can craft a fake session. Add JWT in Spring Boot Security and update the frontend to attach it as a `Bearer` header.
- No server-side note ownership validation. Add `userId` check in backend endpoints.
- Password stored in plain text in the mock db.json. Backend must use BCrypt.
- `bgImage` stores full local asset paths (e.g., `/src/assets/.../food.svg`). Decide on a canonical value (enum, filename, URL) before building the backend schema.
- Search bar in the Header is UI-only — no search endpoint exists yet.
- Reminders and Labels routes are stubs — no backend model needed yet.
