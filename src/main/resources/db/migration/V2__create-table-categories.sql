CREATE TABLE categories(
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    transaction_type ENUM('INCOME', 'EXPENSE') NOT NULL
)