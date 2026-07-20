CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,

    employee_code VARCHAR(20) NOT NULL UNIQUE,

    first_name VARCHAR(100) NOT NULL,

    last_name VARCHAR(100),

    email VARCHAR(255) NOT NULL UNIQUE,

    phone_number VARCHAR(20) NOT NULL,

    department VARCHAR(100) NOT NULL,

    designation VARCHAR(100) NOT NULL,

    status VARCHAR(20) NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL
);
