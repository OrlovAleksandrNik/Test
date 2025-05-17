CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(500) NOT NULL,
    date_of_birth DATE NOT NULL,
    password VARCHAR(500) NOT NULL
);

CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    balance DECIMAL(19,2) NOT NULL CHECK (balance >= 0),
    initial_balance DECIMAL(19,2) NOT NULL,
    last_increase_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE email_data (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    CONSTRAINT fk_user_email FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE phone_data (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    phone VARCHAR(13) NOT NULL UNIQUE,
    CONSTRAINT fk_user_phone FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create indexes for better performance
CREATE INDEX idx_user_name ON users(name);
CREATE INDEX idx_user_date_of_birth ON users(date_of_birth);
CREATE INDEX idx_email_user ON email_data(user_id);
CREATE INDEX idx_phone_user ON phone_data(user_id); 