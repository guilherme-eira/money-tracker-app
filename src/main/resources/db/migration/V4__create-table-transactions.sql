CREATE TABLE transactions(
    id BINARY(16) PRIMARY KEY,
    description VARCHAR(100) NOT NULL,
    value DECIMAL(19,2) NOT NULL,
    transaction_date DATE NOT NULL,
    transaction_type ENUM('INCOME', 'EXPENSE') NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    user_id BINARY(16),
    category_id BINARY(16),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
)