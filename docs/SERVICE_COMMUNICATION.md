# Service Communication

Version: 1.0
Status: Draft
Author: Manmohan Pattnaik
Last Updated: July 2026

---

# 1. Overview

This document defines how microservices communicate with each other in Version 1 of the Enterprise Project Management & Collaboration Platform.

The application follows a synchronous REST-based communication model.

Every service exposes REST APIs.

Services communicate only through these APIs.

No service is allowed to directly access another service's database.

Future versions may introduce asynchronous communication using Kafka for event-driven workflows.

---

# 2. Communication Principles

The system follows these communication principles:

- Service-to-Service communication only through REST APIs
- No shared database access
- Stateless communication
- JSON request/response format
- Versioned APIs
- JWT-secured endpoints
- Loose coupling between services

---

# 3. Request Flow

Client Request

```
Angular

        │

        ▼

Routing Layer

        │

        ▼

Target Service

        │

        ▼

Database
```

Example:

```
Angular

↓

POST /api/v1/projects

↓

Project Service

↓

project_db
```

---

# 4. Service-to-Service Communication

Version 1 uses synchronous REST communication.

Example:

```
Project Service

↓

GET User Details

↓

User Service

↓

user_db
```

No service should bypass another service to access its database.

---

# 5. Communication Matrix

| Caller | Target | Purpose |
|----------|---------|---------|
| Frontend | Auth Service | Login / Refresh Token |
| Frontend | User Service | User Management |
| Frontend | Project Service | Project & Task Management |
| Frontend | Notification Service | Notifications |
| Project Service | User Service | Validate users, retrieve user information |
| Notification Service | User Service | Retrieve user notification preferences |
| Auth Service | User Service | Retrieve user profile (if required) |

This matrix should remain simple and avoid circular dependencies.

---

# 6. Authentication Flow

Login Flow

```
User

↓

Login Request

↓

Auth Service

↓

JWT Access Token

↓

Frontend

↓

Protected API

↓

Business Service
```

Every protected request includes:

```
Authorization: Bearer <access_token>
```

Business services validate the JWT before processing the request.

---

# 7. API Versioning

Every API is versioned.

Example:

```
/api/v1/auth

/api/v1/users

/api/v1/projects

/api/v1/notifications
```

Future versions can introduce `/api/v2` without breaking existing clients.

---

# 8. Request Format

Requests use JSON.

Example:

```json
{
  "name": "Website Redesign",
  "description": "Migration project"
}
```

---

# 9. Response Format

Successful responses should follow a consistent structure.

Example:

```json
{
  "success": true,
  "message": "Project created successfully.",
  "data": {
    "id": "UUID"
  }
}
```

---

# 10. Error Response Format

All services return a standardized error response.

Example:

```json
{
  "timestamp": "2026-07-20T15:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed.",
  "path": "/api/v1/projects"
}
```

---

# 11. HTTP Status Codes

| Status | Usage |
|----------|-------|
| 200 OK | Successful request |
| 201 Created | Resource created |
| 204 No Content | Successful request with no response body |
| 400 Bad Request | Validation failure |
| 401 Unauthorized | Invalid or missing JWT |
| 403 Forbidden | Access denied |
| 404 Not Found | Resource not found |
| 409 Conflict | Duplicate resource |
| 500 Internal Server Error | Unexpected server error |

---

# 12. Validation

Every service validates incoming requests before processing.

Validation includes:

- Required fields
- String length
- Email format
- UUID format
- Business rules

Validation errors should return HTTP 400.

---

# 13. Timeouts

Service-to-service communication should use connection and read timeouts.

Recommended defaults:

Connection Timeout

```
5 seconds
```

Read Timeout

```
10 seconds
```

Timeout values may be adjusted based on deployment requirements.

---

# 14. Retry Strategy

Version 1

Automatic retries are not implemented.

Failures are returned to the caller.

Future versions may introduce retry mechanisms for transient failures.

---

# 15. Logging

Every incoming request should log:

- Timestamp
- HTTP Method
- Endpoint
- Request ID
- Authenticated User
- Response Status
- Processing Time

Sensitive information such as passwords and tokens must never be logged.

---

# 16. Correlation ID

Every request should include a Correlation ID.

Purpose:

- Trace requests across services
- Simplify debugging
- Support distributed logging

If the client does not provide one, the first service should generate it and propagate it to downstream services.

Example header:

```
X-Correlation-ID: 4f6a71fd-5a63-4a4d-a5c3-90d9b46d6d12
```

---

# 17. Health Endpoints

Every service exposes:

```
GET /actuator/health

GET /actuator/info
```

These endpoints are intended for infrastructure monitoring and orchestration.

---

# 18. Future Event-Driven Communication

Future versions may replace some synchronous communication with Kafka.

Example events:

- User Created
- User Updated
- User Deleted
- Project Created
- Project Archived
- Task Assigned
- Task Completed
- Comment Added
- Notification Created

Event-driven communication should be used only when it improves scalability or decoupling.

---

# 19. Communication Guidelines

Services should:

- Communicate only through APIs
- Never access another service's database
- Return consistent responses
- Validate all requests
- Propagate correlation IDs
- Remain stateless
- Keep APIs backward compatible

---

# 20. Summary

Version 1 adopts a simple and maintainable communication model:

- REST APIs
- JSON payloads
- JWT authentication
- Versioned endpoints
- Standardized request/response formats
- Correlation IDs
- Independent service ownership

This approach provides a solid foundation while allowing future evolution toward event-driven architecture.
