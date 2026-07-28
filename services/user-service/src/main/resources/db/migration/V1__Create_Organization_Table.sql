CREATE TABLE organizations
(
    id              UUID PRIMARY KEY,
    name            VARCHAR(150) NOT NULL,
    code            VARCHAR(50) NOT NULL UNIQUE,
    email           VARCHAR(150),
    phone           VARCHAR(30),
    website         VARCHAR(255),
    address         TEXT,
    active          BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP NOT NULL
);
