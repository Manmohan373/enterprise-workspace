CREATE TABLE teams
(
    id UUID PRIMARY KEY,

    project_id UUID NOT NULL,

    name VARCHAR(100) NOT NULL,

    code VARCHAR(50) NOT NULL,

    description VARCHAR(500),

    status VARCHAR(20) NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    version BIGINT NOT NULL,

    CONSTRAINT uk_team_code
        UNIQUE (code),

    CONSTRAINT uk_team_name_project
        UNIQUE (project_id, name),

    CONSTRAINT fk_team_project
        FOREIGN KEY (project_id)
        REFERENCES projects(id)
);
