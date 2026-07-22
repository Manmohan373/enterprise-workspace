# Database Design

Version: 1.0
Status: Draft
Author: Manmohan Pattnaik
Last Updated: July 2026

---

# 1. Overview

The application follows the **Database Per Service** pattern.

Each microservice owns its own PostgreSQL database.

No service is allowed to directly access another service's database.

All cross-service communication must happen through REST APIs (Version 1) and event-driven messaging (Future).

---

# 2. Database Ownership

| Database | Owner Service |
|----------|---------------|
| auth_db | Auth Service |
| user_db | User Service |
| project_db | Project Service |
| notification_db | Notification Service |

Ownership is exclusive.

Only the owning service may perform CRUD operations on its database.

---

# 3. General Database Standards

All databases will follow these standards:

- PostgreSQL
- UTF-8 Encoding
- Snake Case Naming
- UUID Primary Keys
- Soft Delete Support
- Audit Columns
- Flyway Versioned Migrations
- Foreign Keys only within the same database

---

# 4. Common Naming Convention

## Tables

```
users
roles
permissions
projects
tasks
comments
notifications
```

---

## Columns

```
id
name
description
status
created_at
updated_at
created_by
updated_by
deleted
version
```

---

## Primary Keys

Every table uses UUID.

Example

```
id UUID PRIMARY KEY
```

---

## Audit Columns

Every table contains:

```
created_at
updated_at
created_by
updated_by
deleted
version
```

Meaning:

| Column | Description |
|---------|-------------|
| created_at | Record creation timestamp |
| updated_at | Last modification timestamp |
| created_by | User who created the record |
| updated_by | User who last updated the record |
| deleted | Soft delete flag |
| version | Optimistic locking version |

---

# 5. Auth Database (auth_db)

Purpose:

Authentication and Authorization.

Tables:

```
users_credentials
roles
permissions
role_permissions
user_roles
refresh_tokens
password_reset_tokens
```

Responsibilities:

- Store login credentials
- Store encrypted passwords
- Manage roles
- Manage permissions
- Manage refresh tokens

Does NOT store:

- User profile
- Organization
- Teams
- Projects

---

# 6. User Database (user_db)

Purpose:

User and organization management.

Tables:

```
organizations
users
teams
team_members
```

Responsibilities:

- User Profile
- Organizations
- Teams
- Team Membership

Does NOT store:

- Passwords
- Projects
- Notifications

---

# 7. Project Database (project_db)

Purpose:

Project management.

Tables:

```
projects
project_members
sprints
tasks
task_assignments
comments
attachments
```

Responsibilities:

- Projects
- Tasks
- Sprints
- Comments
- Attachments

Does NOT store:

- Login Credentials
- Notifications

---

# 8. Notification Database (notification_db)

Purpose:

Notification management.

Tables:

```
notifications
notification_preferences
```

Responsibilities:

- Store notifications
- Read/Unread status
- Notification preferences

Future:

- Email queue
- SMS queue
- Push notification queue

---

# 9. Relationships

Relationships exist only within the same database.

Example:

```
Project

↓

Tasks

↓

Comments

↓

Attachments
```

No foreign keys across databases.

Example:

❌ Not Allowed

```
task.user_id

FOREIGN KEY

REFERENCES user_db.users(id)
```

Instead,

Store the UUID and retrieve user information using the User Service API.

---

# 10. UUID Strategy

All entities use UUIDs.

Advantages:

- Globally unique
- Safe across microservices
- Easier future data synchronization
- Better suited for distributed systems

Example:

```
550e8400-e29b-41d4-a716-446655440000
```

---

# 11. Soft Delete Strategy

Records are never physically deleted.

Instead:

```
deleted = true
```

Queries should only return:

```
deleted = false
```

Benefits:

- Auditability
- Recovery
- Historical reporting

---

# 12. Optimistic Locking

Every entity includes:

```
version
```

This prevents lost updates during concurrent modifications.

---

# 13. Flyway Migration Strategy

Every service owns its own migrations.

Example:

```
db/

└── migration/

    V1__Initial_Schema.sql

    V2__Create_Users.sql

    V3__Create_Teams.sql

    V4__Add_Project_Status.sql
```

Rules:

- Never modify an existing migration.
- Create a new migration for every schema change.
- Version numbers must always increase.

---

# 14. Indexing Guidelines

Indexes should be created for:

- Primary Keys
- Foreign Keys
- Frequently searched columns
- Unique columns

Examples:

```
email
username
project_name
status
created_at
```

Avoid unnecessary indexes to reduce write overhead.

---

# 15. Data Integrity Rules

- Use NOT NULL wherever appropriate.
- Define UNIQUE constraints for business keys.
- Validate data at both application and database levels.
- Use CHECK constraints where meaningful.

---

# 16. Future Enhancements

Future database improvements may include:

- Read Replicas
- Database Partitioning
- Redis Caching
- Full Text Search
- Archival Strategy
- Data Warehouse Integration

These are outside the scope of Version 1.

---

# 17. Database Design Principles

The database design follows these principles:

- One database per service
- No cross-database joins
- UUID primary keys
- Soft deletes
- Audit columns
- Versioned migrations
- Strong data integrity
- Independent schema evolution
- Cloud-native scalability
