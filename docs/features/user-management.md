# User Management Feature

## Feature Overview

User Management is responsible for managing application users.
It allows administrators to create, update, view, deactivate, and manage users who can access the Enterprise Workspace Management System.

---

# Business Requirement

The system shall allow administrators to manage users securely.

Users should be able to:

- Create User
- Update User
- View User
- List Users
- Activate User
- Deactivate User

The system should maintain audit information for every user.

---

# Scope

### Included

- Create User
- Update User
- Get User by ID
- List Users
- Soft Delete / Deactivate User
- Pagination
- Search
- Validation

### Excluded (Future Sprint)

- Login
- Password Reset
- Profile Picture
- Email Verification
- JWT Authentication
- Role Management

---

# User Story

### Story 1

As an Administrator,
I want to create a user,
so that the user can access the system.

---

### Story 2

As an Administrator,
I want to update user information,
so that employee information remains accurate.

---

### Story 3

As an Administrator,
I want to deactivate users,
so that inactive employees cannot use the system.

---

# Fields

| Field | Type | Required | Description |
|--------|------|----------|-------------|
| id | Long | No | Auto-generated Primary Key |
| employeeCode | String | Yes | Unique Employee Code |
| firstName | String | Yes | First Name |
| lastName | String | No | Last Name |
| email | String | Yes | Unique Email Address |
| phoneNumber | String | Yes | Mobile Number |
| department | String | Yes | Department |
| designation | String | Yes | Job Title |
| status | Enum | Yes | ACTIVE / INACTIVE |
| createdAt | Instant | Auto | Creation Timestamp |
| updatedAt | Instant | Auto | Last Update Timestamp |

---

# Validation Rules

## Employee Code

- Required
- Maximum 20 characters
- Unique

---

## First Name

- Required
- 2–100 characters
- Alphabets only

---

## Email

- Required
- Valid email format
- Unique

---

## Phone Number

- Required
- Digits only
- 10–15 digits

---

## Department

- Required

---

## Status

Allowed values:

- ACTIVE
- INACTIVE

---

# API Design

## Create User

POST /api/v1/users

Request

```json
{
  "employeeCode": "EMP001",
  "firstName": "Manmohan",
  "lastName": "Pattnaik",
  "email": "manmohan@example.com",
  "phoneNumber": "9876543210",
  "department": "Engineering",
  "designation": "Software Engineer"
}
```

Response

HTTP 201

```json
{
  "success": true,
  "message": "User created successfully.",
  "data": {}
}
```

---

## Get User

GET /api/v1/users/{id}

---

## List Users

GET /api/v1/users

Supports:

- Pagination
- Sorting
- Searching

---

## Update User

PUT /api/v1/users/{id}

---

## Deactivate User

PATCH /api/v1/users/{id}/deactivate

---

# Database Design

Table Name

users

| Column | Type | Constraints |
|---------|------|------------|
| id | BIGSERIAL | PK |
| employee_code | VARCHAR(20) | UNIQUE |
| first_name | VARCHAR(100) | NOT NULL |
| last_name | VARCHAR(100) | |
| email | VARCHAR(255) | UNIQUE |
| phone_number | VARCHAR(20) | |
| department | VARCHAR(100) | |
| designation | VARCHAR(100) | |
| status | VARCHAR(20) | NOT NULL |
| created_at | TIMESTAMP | NOT NULL |
| updated_at | TIMESTAMP | NOT NULL |

---

# Exception Scenarios

- Employee Code already exists
- Email already exists
- User not found
- Invalid request payload
- Invalid email
- Invalid phone number

---

# Acceptance Criteria

- User can be created successfully.
- Duplicate email is rejected.
- Duplicate employee code is rejected.
- Validation errors return HTTP 400.
- User retrieval works.
- User update works.
- User can be deactivated.
- Pagination works.

---

# Future Enhancements

- Profile Picture
- User Roles
- Login
- Password Management
- Two Factor Authentication
- Audit History
