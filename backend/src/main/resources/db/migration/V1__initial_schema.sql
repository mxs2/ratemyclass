-- Initial schema creation for RateMyClass application
-- V1__initial_schema.sql

-- Create departments table
CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create index on department code for faster lookups
CREATE INDEX idx_department_code ON departments(code);

-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    student_id VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    major VARCHAR(100),
    graduation_year INTEGER,
    role VARCHAR(20) NOT NULL DEFAULT 'STUDENT' CHECK (role IN ('STUDENT', 'PROFESSOR', 'ADMIN')),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes on users table
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_user_student_id ON users(student_id);
CREATE INDEX idx_user_role ON users(role);

-- Create professors table
CREATE TABLE professors (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    title VARCHAR(100),
    bio VARCHAR(500),
    photo_url VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    department_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE RESTRICT
);

-- Create indexes on professors table
CREATE INDEX idx_professor_name ON professors(first_name, last_name);
CREATE INDEX idx_professor_department ON professors(department_id);

-- Create courses table
CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    credits INTEGER NOT NULL CHECK (credits > 0),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    department_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE RESTRICT
);

-- Create indexes on courses table
CREATE INDEX idx_course_code ON courses(code);
CREATE INDEX idx_course_department ON courses(department_id);
CREATE INDEX idx_course_name ON courses(name);

-- Create course_professors junction table (many-to-many relationship)
CREATE TABLE course_professors (
    id BIGSERIAL PRIMARY KEY,
    course_id BIGINT NOT NULL,
    professor_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    FOREIGN KEY (professor_id) REFERENCES professors(id) ON DELETE CASCADE,
    UNIQUE(course_id, professor_id)
);

-- Create indexes on course_professors table
CREATE INDEX idx_course_professors_course ON course_professors(course_id);
CREATE INDEX idx_course_professors_professor ON course_professors(professor_id);

-- Create course_prerequisites table
CREATE TABLE course_prerequisites (
    id BIGSERIAL PRIMARY KEY,
    course_id BIGINT NOT NULL,
    prerequisite_code VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);

-- Create index on course_prerequisites table
CREATE INDEX idx_course_prerequisites_course ON course_prerequisites(course_id);

-- Create ratings table
CREATE TABLE ratings (
    id BIGSERIAL PRIMARY KEY,
    overall_rating DOUBLE PRECISION NOT NULL CHECK (overall_rating >= 1.0 AND overall_rating <= 5.0),
    teaching_quality DOUBLE PRECISION CHECK (teaching_quality >= 1.0 AND teaching_quality <= 5.0),
    difficulty DOUBLE PRECISION CHECK (difficulty >= 1.0 AND difficulty <= 5.0),
    workload DOUBLE PRECISION CHECK (workload >= 1.0 AND workload <= 5.0),
    clarity DOUBLE PRECISION CHECK (clarity >= 1.0 AND clarity <= 5.0),
    review_text TEXT CHECK (LENGTH(review_text) <= 2000),
    would_take_again BOOLEAN NOT NULL DEFAULT FALSE,
    is_anonymous BOOLEAN NOT NULL DEFAULT FALSE,
    semester VARCHAR(20),
    year INTEGER CHECK (year >= 2000 AND year <= 2030),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    user_id BIGINT NOT NULL,
    professor_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (professor_id) REFERENCES professors(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    UNIQUE(user_id, professor_id, course_id)
);

-- Create indexes on ratings table
CREATE INDEX idx_rating_user ON ratings(user_id);
CREATE INDEX idx_rating_professor ON ratings(professor_id);
CREATE INDEX idx_rating_course ON ratings(course_id);
CREATE INDEX idx_rating_created ON ratings(created_at);
CREATE INDEX idx_rating_overall ON ratings(overall_rating);
CREATE INDEX idx_rating_active ON ratings(active);

-- Create composite indexes for common queries
CREATE INDEX idx_rating_professor_active ON ratings(professor_id, active);
CREATE INDEX idx_rating_course_active ON ratings(course_id, active);
CREATE INDEX idx_rating_user_active ON ratings(user_id, active);

-- Create function to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Create triggers to automatically update updated_at column
CREATE TRIGGER update_departments_updated_at BEFORE UPDATE ON departments FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_professors_updated_at BEFORE UPDATE ON professors FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_courses_updated_at BEFORE UPDATE ON courses FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_ratings_updated_at BEFORE UPDATE ON ratings FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();