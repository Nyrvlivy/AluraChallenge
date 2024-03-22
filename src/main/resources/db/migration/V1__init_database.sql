CREATE TABLE IF NOT EXISTS `roles`
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS `users`
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(20) UNIQUE  NOT NULL,
    email      VARCHAR(320) UNIQUE NOT NULL,
    password   VARCHAR(100)        NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role_id    INT                 NOT NULL,
    FOREIGN KEY (role_id) REFERENCES `roles` (id)
);