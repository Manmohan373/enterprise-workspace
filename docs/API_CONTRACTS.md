# API Contracts

Version: 1.0
Status: Draft
Author: Manmohan Pattnaik
Last Updated: July 2026

---

# 1. Overview

This document defines the public REST APIs exposed by each microservice.

Guidelines:

- All APIs are RESTful.
- All APIs are versioned.
- Request and response bodies use JSON.
- Protected endpoints require JWT authentication.
- UUID is used as the primary identifier.
- Validation errors return HTTP 400.

Base URL

/api/v1

---

# 2. Authentication Service

Base Path

/api/v1/auth

## Login

POST /login

Description

Authenticate a user and return JWT tokens.

Request

```json
{
  "email": "john@example.com",
  "password": "Password@123"
}
```

Response (200)

```json
{
  "accessToken": "...",
  "refreshToken": "...",
  "expiresIn": 3600
}
```

---

## Refresh Token

POST /refresh

Request

```json
{
  "refreshToken": "..."
}
```

Response (200)

```json
{
  "accessToken": "...",
  "refreshToken": "..."
}
```

---

## Logout

POST /logout

Description

Invalidate refresh token.

---

## Forgot Password

POST /forgot-password

---

## Reset Password

POST /reset-password

---

## Change Password

POST /change-password

Authentication Required

Yes

---

# 3. User Service

Base Path

/api/v1/users

---

## Create User

POST /

Request

```json
{
  "firstName": "",
  "lastName": "",
  "email": "",
  "phone": "",
  "organizationId": "",
  "teamId": ""
}
```

Response

201 Created

---

## Get User

GET /{userId}

---

## Update User

PUT /{userId}

---

## Delete User (Soft Delete)

DELETE /{userId}

---

## Search Users

GET /

Supports

- Pagination
- Sorting
- Filtering

Example

GET /users?page=0&size=10

---

## Activate User

PATCH /{userId}/activate

---

## Deactivate User

PATCH /{userId}/deactivate

---

# 4. Organization APIs

Base Path

/api/v1/organizations

Endpoints

POST /

GET /

GET /{organizationId}

PUT /{organizationId}

DELETE /{organizationId}

---

# 5. Team APIs

Base Path

/api/v1/teams

Endpoints

POST /

GET /

GET /{teamId}

PUT /{teamId}

DELETE /{teamId}

---

## Team Members

POST /{teamId}/members

DELETE /{teamId}/members/{userId}

GET /{teamId}/members

---

# 6. Project Service

Base Path

/api/v1/projects

---

## Create Project

POST /

---

## Update Project

PUT /{projectId}

---

## Get Project

GET /{projectId}

---

## List Projects

GET /

Supports

- Pagination
- Search
- Sorting
- Filters

---

## Archive Project

PATCH /{projectId}/archive

---

## Delete Project

DELETE /{projectId}

Soft Delete

---

## Project Members

POST /{projectId}/members

DELETE /{projectId}/members/{userId}

GET /{projectId}/members

---

# 7. Sprint APIs

Base Path

/api/v1/sprints

Endpoints

POST /

GET /

GET /{sprintId}

PUT /{sprintId}

DELETE /{sprintId}

PATCH /{sprintId}/start

PATCH /{sprintId}/complete

---

# 8. Task APIs

Base Path

/api/v1/tasks

---

## Create Task

POST /

---

## Get Task

GET /{taskId}

---

## Update Task

PUT /{taskId}

---

## Delete Task

DELETE /{taskId}

---

## Assign Task

PATCH /{taskId}/assign

---

## Change Status

PATCH /{taskId}/status

---

## Add Labels

PATCH /{taskId}/labels

---

## Search Tasks

GET /

Supports

- Pagination
- Search
- Filter
- Sort

---

# 9. Comment APIs

Base Path

/api/v1/comments

Endpoints

POST /

PUT /{commentId}

DELETE /{commentId}

GET /task/{taskId}

---

# 10. Attachment APIs

Base Path

/api/v1/attachments

Endpoints

POST /

GET /{attachmentId}

DELETE /{attachmentId}

GET /task/{taskId}

Multipart upload supported.

---

# 11. Notification Service

Base Path

/api/v1/notifications

---

## Get Notifications

GET /

---

## Mark as Read

PATCH /{notificationId}/read

---

## Mark All as Read

PATCH /read-all

---

## Delete Notification

DELETE /{notificationId}

---

## Notification Preferences

GET /preferences

PUT /preferences

---

# 12. Dashboard APIs

Base Path

/api/v1/dashboard

Endpoints

GET /personal

GET /organization

GET /project/{projectId}

---

# 13. Audit APIs

Base Path

/api/v1/audit

Endpoints

GET /

GET /user/{userId}

GET /project/{projectId}

Search supported.

---

# 14. Request Standards

Every request should:

- Use JSON
- Include JWT (except login)
- Include Correlation ID
- Use UUID identifiers
- Follow validation rules

---

# 15. Response Standards

Successful responses

200 OK

201 Created

204 No Content

Standard response example

```json
{
  "success": true,
  "message": "Operation completed successfully.",
  "data": {}
}
```

---

# 16. Error Response

```json
{
  "timestamp": "",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed.",
  "path": "/api/v1/users"
}
```

---

# 17. Validation Rules

General rules:

- Required fields must not be null.
- Email must be valid.
- UUIDs must be valid.
- String lengths should be validated.
- Dates must be valid.
- Uploaded files should be validated.

Business validation is performed in the service layer.

---

# 18. API Versioning

All APIs are versioned.

Current version

/api/v1

Future versions

/api/v2

Older versions should remain backward compatible whenever possible.

---

# 19. Security

Protected APIs require:

Authorization

```
Bearer <JWT_TOKEN>
```

Public APIs

- Login
- Refresh Token
- Forgot Password
- Reset Password

All other APIs require authentication.

---

# 20. Summary

The API layer follows these principles:

- RESTful design
- Versioned endpoints
- Stateless communication
- JWT authentication
- Standard request/response models
- Consistent error handling
- Pagination support
- Search and filtering support
- UUID identifiers
