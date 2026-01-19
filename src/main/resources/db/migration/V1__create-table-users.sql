CREATE TABLE users(
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    document CHAR(11) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    password_reset_token BINARY(16),
    token_expiration DATETIME,
    created_at DATETIME NOT NULL,
    updated_at DATETIME
)