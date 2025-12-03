--liquibase formatted sql

-- changeset amorozova:1
INSERT INTO users (name, username, email, password, role) VALUES
('user', 'user', 'admin@velocity.ru', '$2a$12$ek8UUHtoFf0K5iRzLigDjOgOhj0h2g9VYlRlGMMDwHKh1bsNMMqVy', 'ROLE_USER'),
('developer', 'developer', 'dev@velocity.ru', '$2a$12$ek8UUHtoFf0K5iRzLigDjOgOhj0h2g9VYlRlGMMDwHKh1bsNMMqVy', 'ROLE_USER'),
('admin', 'admin', 'dev@velocity.ru', '$2a$12$ek8UUHtoFf0K5iRzLigDjOgOhj0h2g9VYlRlGMMDwHKh1bsNMMqVy', 'ROLE_ADMIN');

-- changeset amorozova:2
INSERT INTO projects (name, user_id) VALUES
('Velocity Development', 1),
('SomeProject Development', 2);

-- changeset amorozova:3
INSERT INTO columns (name, project_id) VALUES
('To Do', 1),
('In Progress', 1),
('Review', 1),
('Done', 1);

-- changeset amorozova:4
INSERT INTO tasks (name, description, project_id, executor_id, column_id, start_date, deadline) VALUES
('Setup database', 'Configure PostgreSQL with Liquibase', 1, 2, 1, '2024-01-10', '2024-01-15'),
('Design architecture', 'Create system architecture diagram', 1, 3, 2, '2024-01-12', '2024-01-20'),
('Implement authentication', 'Develop user authentication system', 1, 2, 2, '2024-01-15', '2024-01-25'),
('Code review', 'Review pull requests', 1, 1, 3, '2024-01-18', '2024-01-22'),
('Deploy to production', 'Deploy application to production server', 1, 3, 4, '2024-01-20', '2024-01-30');