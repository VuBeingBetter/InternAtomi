CREATE DATABASE IF NOT EXISTS user_management;
USE user_management;

CREATE TABLE Users (
    id VARCHAR(50) PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    role ENUM('ADMIN', 'CLIENT') NOT NULL
);

-- Chèn Admin mẫu để có thể đăng nhập lần đầu
INSERT INTO Users (id, username, password, firstName, lastName, role)
VALUES ('admin-uuid-001', 'admin001', 'admin123', 'System', 'Admin', 'ADMIN');