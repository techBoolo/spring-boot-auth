# Spring Boot Authentication Demo

A comprehensive Spring Boot project demonstrating multiple authentication strategies across different Git branches. This repository serves as a reference for implementing Basic Auth, Session-based Auth, and Token-based (JWT) Authentication in a Spring Boot 3+ (specifically 4.0.1) application.

## 🌿 Branches & Authentication Strategies

The project is structured into separate branches, each focusing on a specific authentication mechanism:

| Branch | Description | Key Features |
| :--- | :--- | :--- |
| **`main` / `basic-auth`** | **Basic Authentication** | Standard HTTP Basic Auth (`Authorization: Basic <credentials>`). Stateless at the protocol level, but relies on browser/client handling of headers. Default Spring Security behavior. |
| **`session-auth`** | **Session-Based Authentication** | Stateful authentication using `JSESSIONID`. The server maintains a session for the user after login. Configured with `SessionCreationPolicy.IF_REQUIRED`. |
| **`token-auth`** | **JWT Authentication** | Stateless authentication using **JSON Web Tokens (JWT)**. A custom `JwtAuthenticationFilter` intercepts requests to validate the `Authorization: Bearer <token>` header. Suitable for modern SPAs and Mobile clients. |

## 🚀 Getting Started

### Prerequisites

*   **Java 25** (Ensure your environment supports this version, as specified in `pom.xml`)
*   **Maven**

### Installation

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd auth-git
    ```

2.  **Switch to the desired branch:**
    ```bash
    # For Basic Auth
    git checkout basic-auth

    # For Session Auth
    git checkout session-auth

    # For JWT Token Auth
    git checkout token-auth
    ```

3.  **Run the application:**
    ```bash
    ./mvnw spring-boot:run
    ```

The application will start on `http://localhost:8080` (default port).

## 🔌 API Endpoints

The following key endpoints are available (structure is consistent across branches, but auth behavior varies):

| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| `GET` | `/` | Root endpoint (Health check) | ✅ Yes |
| `GET` | `/health-check` | Simple health check endpoint | ❌ No (Public) |
| `POST` | `/users/register` | Register a new user | ❌ No (Public) |
| `POST` | `/users/login` | Authenticate user. Returns success msg or Token (in `token-auth`). | ❌ No (Public) |
| `GET` | `/users` | List all users | ✅ Yes |
| `GET` | `/users/{id}` | Get user details by ID | ✅ Yes |

### 🔐 Authentication Details by Branch

#### `basic-auth` / `main`
*   **Method**: Send `Authorization` header with every request.
*   **Format**: `Basic base64(email:password)`

#### `session-auth`
*   **Method**: Login once via `/users/login` or Basic Auth.
*   **Mechanism**: The server sets a `JSESSIONID` cookie. Subsequent requests must include this cookie.

#### `token-auth`
*   **Method**:
    1.  Call `/users/login` with credentials.
    2.  Receive `accessToken` in the JSON response.
    3.  Send `Authorization` header with every subsequent request.
*   **Format**: `Bearer <your_access_token>`

## 🛠️ Tech Stack

*   Spring Boot 4.0.1
*   Spring Security
*   Spring Data JPA
*   H2 Database (In-memory)
*   Lombok
*   MapStruct
*   Java 25
