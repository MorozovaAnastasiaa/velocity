--liquibase formatted sql

-- changeset amorozova:1
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50),
    username VARCHAR(50),
    email VARCHAR(100),
    password VARCHAR(255),
    role VARCHAR(255)
);

-- changeset amorozova:2
CREATE TABLE projects (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    user_id BIGINT
);

-- changeset amorozova:3
CREATE TABLE columns (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    project_id BIGINT
);

-- changeset amorozova:4
CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE,
    deadline DATE,
    executor_id BIGINT,
    project_id BIGINT,
    column_id BIGINT,
    created_at TIMESTAMP DEFAULT NOW()
);

ALTER TABLE projects
ADD CONSTRAINT fk_projects_user
FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE tasks
ADD CONSTRAINT fk_tasks_executor
FOREIGN KEY (executor_id) REFERENCES users(id);

ALTER TABLE tasks
ADD CONSTRAINT fk_tasks_project
FOREIGN KEY (project_id) REFERENCES projects(id);

ALTER TABLE tasks
ADD CONSTRAINT fk_tasks_column
FOREIGN KEY (column_id) REFERENCES columns(id);

ALTER TABLE columns
ADD CONSTRAINT fk_columns_project
FOREIGN KEY (project_id) REFERENCES projects(id);