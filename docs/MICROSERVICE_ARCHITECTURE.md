# Microservice Architecture

Version: 1.0
Status: Draft
Author: Manmohan Pattnaik
Last Updated: July 2026

---

# 1. Overview

The Enterprise Project Management & Collaboration Platform follows a distributed microservice architecture where each business capability is implemented as an independent Spring Boot application.

Each service:

- Is independently deployable
- Owns its own database
- Has its own source code repository
- Has its own Docker image
- Has its own Flyway migrations
- Can be developed independently
- Can be scaled independently

Services communicate synchronously using REST APIs during Version 1.

Future versions will introduce asynchronous communication using Kafka for event-driven workflows.

---

# 2. Architecture Principles

The project follows the following principles:

## Service Independence

Every service is an independent application.

No shared business logic.

No shared database.

No shared deployment.

---

## Database Per Service

Every service owns its own database.

Only the owning service can access its database.

Other services must communicate through APIs.

Example:

✅ Correct

Project Service
│
▼
User Service API
│
▼
user_db

❌ Incorrect

Project Service

↓

SELECT * FROM user_db.users

---

## Stateless Services

All services remain stateless.

Authentication information is carried using JWT tokens.

No HTTP session state is maintained.

---

## API First

Every interaction between services happens through documented REST APIs.

No hidden communication.

---

## Loose Coupling

Services know only:

- API Contract
- Endpoint
- Request
- Response

They never depend on another service's implementation.

---

# 3. Project Structure

enterprise-workspace/

```
docs/
frontend/
infrastructure/
postman/

services/

    auth-service/

    user-service/

    project-service/

    notification-service/
```

Every service is an independent Spring Boot application.

---

# 4. Microservices

## Auth Service

Responsibilities

- Login
- Logout
- JWT Generation
- Refresh Token
- Password Management
- Authentication
- Authorization
- Roles
- Permissions

Owns

- auth_db

Exposes

- Authentication APIs

Never manages

- Projects
- Tasks
- Notifications

---

## User Service

Responsibilities

- Users
- Organizations
- Teams
- User Profiles
- Search Users

Owns

- user_db

Exposes

- User APIs

Never manages

- Authentication
- Projects

---

## Project Service

Responsibilities

- Projects
- Tasks
- Sprints
- Comments
- Attachments
- Dashboard

Owns

- project_db

Exposes

- Project APIs

Never manages

- Authentication
- User Credentials

---

## Notification Service

Responsibilities

- Notifications
- Notification History
- Notification Preferences

Future

- Email
- SMS
- Push Notifications

Owns

- notification_db

---

# 5. Service Responsibilities

| Service | Responsibility |
|----------|---------------|
| Auth Service | Authentication & Authorization |
| User Service | User, Organization & Team Management |
| Project Service | Projects, Tasks & Collaboration |
| Notification Service | Notifications & Messaging |

---

# 6. Service Communication

Version 1

REST APIs

Example

Project Service

↓

User Service

↓

User Details

Future

Kafka Events

Project Created

↓

Notification Service

Task Assigned

↓

Notification Service

User Deleted

↓

Project Service

---

# 7. Database Ownership

| Database | Owner |
|-----------|-------|
| auth_db | Auth Service |
| user_db | User Service |
| project_db | Project Service |
| notification_db | Notification Service |

Rule:

Only the owning service may access its database.

---

# 8. Ports (Development)

| Service | Port |
|----------|------|
| Frontend | 4200 |
| Auth Service | 8081 |
| User Service | 8082 |
| Project Service | 8083 |
| Notification Service | 8084 |

These ports are configurable.

---

# 9. API Routing

Initial deployment may use a reverse proxy for routing requests.

Example routes:

/api/v1/auth/**

↓

Auth Service

/api/v1/users/**

↓

User Service

/api/v1/projects/**

↓

Project Service

/api/v1/notifications/**

↓

Notification Service

The routing layer is an infrastructure concern and can be replaced (e.g., Nginx, Spring Cloud Gateway, Kubernetes Ingress) without changing business services.

---

# 10. Authentication Flow

User

↓

Login Request

↓

Auth Service

↓

JWT

↓

Client

↓

Protected API

↓

Business Service

↓

JWT Validation

↓

Business Logic

---

# 11. Error Handling

Every service returns a common error structure.

Example

{
"timestamp": "...",
"status": 404,
"error": "Not Found",
"message": "...",
"path": "/api/v1/users/1"
}

---

# 12. Logging

Every service maintains independent logs.

Logs should include:

- Timestamp
- Request ID
- User ID (if authenticated)
- Service Name
- Log Level
- Message

Future versions may centralize logs using ELK or Grafana Loki.

---

# 13. Health Checks

Every service exposes health endpoints.

Example

/actuator/health

/actuator/info

These endpoints will be used by Docker and Kubernetes for monitoring.

---

# 14. Scalability

Each service can be scaled independently.

Example

2 x User Service

3 x Project Service

1 x Auth Service

without affecting other services.

---

# 15. Future Enhancements

Future architecture improvements include:

- Kafka Event Streaming
- Kubernetes Deployment
- Distributed Tracing
- Centralized Logging
- API Rate Limiting
- API Gateway
- Circuit Breaker
- Service Mesh
- Distributed Cache (Redis)
- Observability Dashboard

---

# 16. Architecture Summary

The system is designed with the following goals:

- Independent services
- Independent databases
- REST-based communication
- Stateless authentication
- Loose coupling
- Horizontal scalability
- Container-first deployment
- Cloud-native readiness
