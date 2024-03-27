CREATE TABLE IF NOT EXISTS `roles`
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name ENUM ('ROLE_STUDENT', 'ROLE_INSTRUCTOR', 'ROLE_ADMIN') UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS `users`
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255)        NOT NULL,
    username   VARCHAR(20) UNIQUE  NOT NULL,
    email      VARCHAR(320) UNIQUE NOT NULL,
    password   VARCHAR(100)        NOT NULL,
    role_id    INT                 NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES `roles` (id)
);

CREATE TABLE IF NOT EXISTS `courses`
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255)       NOT NULL,
    code          VARCHAR(10) UNIQUE NOT NULL,
    instructor_id BIGINT             NOT NULL,
    description   TEXT,
    status        BOOLEAN   DEFAULT 1,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    inactive_at   TIMESTAMP DEFAULT NULL,
    foreign key (instructor_id) references `users` (id)
);
