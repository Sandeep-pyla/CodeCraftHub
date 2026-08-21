# CodeCraftHub

CodeCraftHub is a simple Spring Boot REST API for tracking developer learning courses.

The project is designed as a beginner-friendly introduction to building REST APIs with Java and Spring Boot. It uses a local `courses.json` file for data persistence instead of a database.

## Features

* Create, read, update, and delete courses
* JSON file-based data storage
* Automatic course ID generation starting from `1`
* Automatic `created_at` timestamp
* Course target dates using `YYYY-MM-DD` format
* Course status validation
* Required-field validation
* Course-not-found error handling
* JSON file read/write error handling
* Global REST API exception handling
* No database required
* No authentication or user management

### Supported Course Statuses

A course status must be exactly one of:

* `Not Started`
* `In Progress`
* `Completed`

## Technology Stack

* Java 17+
* Spring Boot 4.1.0
* Spring Web
* Spring Boot Validation
* Jackson 3
* Maven
* JSON file storage

## Project Structure

```text
CodeCraftHub/
├── courses.json
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── CodeCraftHub/
        │           ├── CodeCraftHubApplication.java
        │           ├── controller/
        │           │   └── CourseController.java
        │           ├── exception/
        │           │   ├── CourseNotFoundException.java
        │           │   ├── FileStorageException.java
        │           │   └── GlobalExceptionHandler.java
        │           ├── model/
        │           │   └── Course.java
        │           └── service/
        │               └── CourseService.java
        └── resources/
            └── application.properties
```

## Installation

### Prerequisites

Make sure the following are installed:

* Java 17 or newer
* Maven 3.9+
* Git (optional)

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

### Clone the Project

If the project is hosted in Git:

```bash
git clone <repository-url>
cd CodeCraftHub
```

Alternatively, open the existing CodeCraftHub project in IntelliJ IDEA or VS Code.

### Install Maven Dependencies

From the project root directory:

```bash
mvn clean install
```

Maven will download the required Spring Boot, Jackson, validation, and testing dependencies.

## Running the Application

### Option 1: Maven

Run:

```bash
mvn spring-boot:run
```

The application starts on:

```text
http://localhost:8080
```

### Option 2: Run from an IDE

Open:

```text
CodeCraftHubApplication.java
```

and run the `main()` method.

The application will start on port `8080`.

## JSON Data Storage

CodeCraftHub does not use a database.

Course information is stored in:

```text
courses.json
```

If the file does not exist, the application automatically creates it with:

```json
[]
```

After adding courses, the file will contain data similar to:

```json
[
  {
    "id": 1,
    "name": "Spring Boot",
    "description": "Learn Spring Boot REST API development",
    "target_date": "2026-09-15",
    "status": "In Progress",
    "created_at": "2026-08-20T21:15:30"
  }
]
```

## Course JSON Fields

| Field         | Required | Description                                   |
| ------------- | -------- | --------------------------------------------- |
| `id`          | No       | Automatically generated                       |
| `name`        | Yes      | Course name                                   |
| `description` | Yes      | Course description                            |
| `target_date` | Yes      | Target completion date in `YYYY-MM-DD` format |
| `status`      | Yes      | `Not Started`, `In Progress`, or `Completed`  |
| `created_at`  | No       | Automatically generated timestamp             |

The client does not need to provide `id` or `created_at`.

## API Documentation

Base URL:

```text
http://localhost:8080/api/courses
```

---

## 1. Create a Course

### Request

```http
POST /api/courses
Content-Type: application/json
```

### Request Body

```json
{
  "name": "Spring Boot",
  "description": "Learn Spring Boot REST API development",
  "target_date": "2026-09-15",
  "status": "Not Started"
}
```

### cURL

```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"Spring Boot\",\"description\":\"Learn Spring Boot REST API development\",\"target_date\":\"2026-09-15\",\"status\":\"Not Started\"}"
```

### Response

```http
201 Created
```

```json
{
  "id": 1,
  "name": "Spring Boot",
  "description": "Learn Spring Boot REST API development",
  "target_date": "2026-09-15",
  "status": "Not Started",
  "created_at": "2026-08-20T21:15:30"
}
```

---

## 2. Get All Courses

### Request

```http
GET /api/courses
```

### cURL

```bash
curl http://localhost:8080/api/courses
```

### Response

```http
200 OK
```

```json
[
  {
    "id": 1,
    "name": "Spring Boot",
    "description": "Learn Spring Boot REST API development",
    "target_date": "2026-09-15",
    "status": "Not Started",
    "created_at": "2026-08-20T21:15:30"
  },
  {
    "id": 2,
    "name": "Docker",
    "description": "Learn Docker fundamentals",
    "target_date": "2026-10-01",
    "status": "In Progress",
    "created_at": "2026-08-20T21:20:15"
  }
]
```

If there are no courses:

```json
[]
```

---

## 3. Get a Course by ID

### Request

```http
GET /api/courses/{id}
```

Example:

```http
GET /api/courses/1
```

### cURL

```bash
curl http://localhost:8080/api/courses/1
```

### Response

```http
200 OK
```

```json
{
  "id": 1,
  "name": "Spring Boot",
  "description": "Learn Spring Boot REST API development",
  "target_date": "2026-09-15",
  "status": "Not Started",
  "created_at": "2026-08-20T21:15:30"
}
```

### Course Not Found

If the course doesn't exist:

```http
GET /api/courses/999
```

Response:

```http
404 Not Found
```

```json
{
  "error": "Course with id 999 was not found"
}
```

---

## 4. Update a Course

### Request

```http
PUT /api/courses/{id}
```

Example:

```http
PUT /api/courses/1
Content-Type: application/json
```

### Request Body

```json
{
  "name": "Spring Boot REST API",
  "description": "Learn Spring Boot REST APIs and CRUD operations",
  "target_date": "2026-09-20",
  "status": "In Progress"
}
```

### cURL

```bash
curl -X PUT http://localhost:8080/api/courses/1 \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"Spring Boot REST API\",\"description\":\"Learn Spring Boot REST APIs and CRUD operations\",\"target_date\":\"2026-09-20\",\"status\":\"In Progress\"}"
```

### Response

```http
200 OK
```

```json
{
  "id": 1,
  "name": "Spring Boot REST API",
  "description": "Learn Spring Boot REST APIs and CRUD operations",
  "target_date": "2026-09-20",
  "status": "In Progress",
  "created_at": "2026-08-20T21:15:30"
}
```

The following values are preserved during an update:

* `id`
* `created_at`

---

## 5. Delete a Course

### Request

```http
DELETE /api/courses/{id}
```

Example:

```http
DELETE /api/courses/1
```

### cURL

```bash
curl -X DELETE http://localhost:8080/api/courses/1
```

### Response

```http
204 No Content
```

A successful DELETE request does not return a response body.

## Validation

The API validates required fields.

### Missing Name

Request:

```json
{
  "description": "Learn Spring Boot",
  "target_date": "2026-09-15",
  "status": "Not Started"
}
```

Response:

```http
400 Bad Request
```

```json
{
  "name": "Name is required"
}
```

### Invalid Status

Request:

```json
{
  "name": "Spring Boot",
  "description": "Learn Spring Boot",
  "target_date": "2026-09-15",
  "status": "Started"
}
```

Response:

```http
400 Bad Request
```

```json
{
  "status": "Status must be exactly one of: Not Started, In Progress, Completed"
}
```

### Invalid Target Date

The correct format is:

```text
YYYY-MM-DD
```

Example:

```json
{
  "name": "Spring Boot",
  "description": "Learn Spring Boot",
  "target_date": "2026-09-15",
  "status": "Not Started"
}
```

An invalid value such as:

```json
"target_date": "09/15/2026"
```

will result in:

```http
400 Bad Request
```

## HTTP Status Codes

| Status                      | Meaning                               |
| --------------------------- | ------------------------------------- |
| `200 OK`                    | Request completed successfully        |
| `201 Created`               | Course successfully created           |
| `204 No Content`            | Course successfully deleted           |
| `400 Bad Request`           | Invalid request or validation failure |
| `404 Not Found`             | Course does not exist                 |
| `500 Internal Server Error` | Server or file storage error          |

# Troubleshooting

## 1. `localhost:8080` shows an unexpected error

The application API is under:

```text
http://localhost:8080/api/courses
```

Use:

```text
http://localhost:8080/api/courses
```

instead of only:

```text
http://localhost:8080
```

The root `/` endpoint is not defined.

---

## 2. `courses.json` is missing

The application automatically creates the file when it starts.

If it does not exist, check that the application has permission to write to the project directory.

The initial contents should be:

```json
[]
```

---

## 3. `courses.json` contains invalid JSON

If the file was manually edited and contains malformed JSON, the application may return a file-related error.

For example, this is invalid:

```json
[
  {
    "id": 1,
    "name": "Spring Boot"
```

If you don't need the existing data, replace the contents with:

```json
[]
```

Then restart the application.

---

## 4. Maven cannot delete the `target` directory

You may see an error similar to:

```text
Failed to clean project:
Failed to delete ...\target\classes
```

First stop the running Spring Boot application.

Then delete the `target` directory manually:

```text
CodeCraftHub/
└── target/
```

The `target` directory contains generated Maven build files and can safely be deleted.

Run:

```bash
mvn clean install
```

again.

If the project is stored inside a OneDrive directory, OneDrive may temporarily lock generated Maven files. Moving the project to a location such as:

```text
C:\projects\CodeCraftHub
```

can help avoid this problem.

---

## 5. `TypeReference` cannot be resolved

CodeCraftHub uses Spring Boot 4.1.0, which uses Jackson 3.

Use:

```java
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
```

Do not use the older Jackson 2 import:

```java
import com.fasterxml.jackson.core.type.TypeReference;
```

You can verify the Jackson dependencies with:

```bash
mvn dependency:tree
```

You should see Jackson 3 dependencies such as:

```text
tools.jackson.core:jackson-core
tools.jackson.core:jackson-databind
```

---

## 6. Maven build succeeds but the IDE shows import errors

Refresh the Maven project in your IDE.

You can also run:

```bash
mvn clean install
```

Then restart the IDE if necessary.

---

## 7. Port 8080 is already in use

If Spring Boot reports that port `8080` is already in use, either stop the application currently using that port or change the port in:

```text
src/main/resources/application.properties
```

For example:

```properties
server.port=8081
```

The API would then be available at:

```text
http://localhost:8081/api/courses
```

---

## 8. File read/write errors

If the API returns:

```json
{
  "error": "Unable to read or write courses.json."
}
```

check:

1. Whether `courses.json` exists.
2. Whether it contains valid JSON.
3. Whether the application has permission to access the project directory.
4. Whether another application is locking the file.
5. Whether OneDrive is synchronizing or locking the project files.

## Learning Goals

CodeCraftHub is intentionally simple so that beginners can focus on the fundamentals of REST API development.

The project demonstrates:

```text
HTTP Request
     ↓
Controller
     ↓
Service
     ↓
Jackson ObjectMapper
     ↓
courses.json
```

The application also demonstrates common REST concepts including:

* HTTP GET
* HTTP POST
* HTTP PUT
* HTTP DELETE
* HTTP status codes
* Request bodies
* Path variables
* JSON serialization/deserialization
* Validation
* Exception handling
* Service-layer separation
* File-based persistence

## Future Improvements

Once the basic application is working, possible next steps include:

* Add a React frontend
* Add course filtering by status
* Add search by course name
* Add pagination
* Add unit and integration tests
* Add Spring Boot Actuator
* Add logging
* Replace JSON storage with PostgreSQL
* Introduce Spring Data JPA
* Add authentication and user-specific courses
* Containerize the application with Docker
* Deploy the application to AWS
