-- Insert test users with BCrypt-encoded passwords (password: 'password123')
INSERT INTO users (name, date_of_birth, password) VALUES
    ('John Doe', '1990-01-15', '$2a$10$xn3LI/AjqicFYZFruSwve.ODd6/GiHtS9C.fD2YwS6oAjYa5ylWPC'),
    ('Jane Smith', '1985-05-20', '$2a$10$xn3LI/AjqicFYZFruSwve.ODd6/GiHtS9C.fD2YwS6oAjYa5ylWPC');

-- Insert accounts with initial balance
INSERT INTO accounts (user_id, balance, initial_balance) VALUES
    (1, 1000.00, 1000.00),
    (2, 2000.00, 2000.00);

-- Insert email data
INSERT INTO email_data (user_id, email) VALUES
    (1, 'john.doe@example.com'),
    (1, 'john.work@example.com'),
    (2, 'jane.smith@example.com');

-- Insert phone data
INSERT INTO phone_data (user_id, phone) VALUES
    (1, '79201234567'),
    (1, '79207654321'),
    (2, '79209876543'); 