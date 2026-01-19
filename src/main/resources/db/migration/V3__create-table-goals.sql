CREATE TABLE goals(
    id BINARY(16) PRIMARY KEY,
    max_expense DECIMAL(19, 2) NOT NULL,
    start_date DATE NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    user_id BINARY(16),
    category_id BINARY(16) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
)