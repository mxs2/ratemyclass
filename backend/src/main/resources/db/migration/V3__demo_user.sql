-- Demo user for testing
-- V3__demo_user.sql

-- Insert demo user with credentials: demo@university.edu / Demo123!
-- Password is BCrypt hashed version of "Demo123!"
INSERT INTO users (email, password, first_name, last_name, student_id, major, graduation_year, role, email_verified, created_at, updated_at) 
VALUES (
    'demo@university.edu',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', -- Demo123!
    'Demo',
    'User',
    'DEMO12345',
    'Computer Science',
    2025,
    'STUDENT',
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
