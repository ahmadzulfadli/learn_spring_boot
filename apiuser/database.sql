CREATE DATABASE spring_boot_user_api;

use spring_boot_user_api;

CREATE TABLE users(
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    token VARCHAR(100),
    token_expired_at BIGINT,
    PRIMARY KEY (username),
    UNIQUE(token)
)ENGINE InnoDB;

SELECT * FROM users;
DESC users;
