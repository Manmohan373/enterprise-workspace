CREATE TABLE projects
(
    id UUID PRIMARY KEY,

    organization_id UUID NOT NULL,

    name VARCHAR(150) NOT NULL,

    code VARCHAR(50) NOT NULL,

    client_name VARCHAR(150),

    description VARCHAR(1000),

    project_type VARCHAR(30) NOT NULL,

    status VARCHAR(30) NOT NULL,

    start_date TIMESTAMP,

    end_date TIMESTAMP,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    version BIGINT NOT NULL,

    CONSTRAINT fk_projects_organization
        FOREIGN KEY (organization_id)
        REFERENCES organizations(id),

    CONSTRAINT uk_projects_code
        UNIQUE (code),

    CONSTRAINT uk_projects_name_organization
        UNIQUE (organization_id, name)
);
