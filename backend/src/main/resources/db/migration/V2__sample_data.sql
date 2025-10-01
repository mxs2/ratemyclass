-- Sample data for development and testing
-- V2__sample_data.sql

-- Insert sample departments
INSERT INTO departments (code, name, description) VALUES
('CS', 'Computer Science', 'Department of Computer Science and Software Engineering'),
('MATH', 'Mathematics', 'Department of Mathematics and Statistics'),
('PHYS', 'Physics', 'Department of Physics and Astronomy'),
('CHEM', 'Chemistry', 'Department of Chemistry and Biochemistry'),
('ENG', 'Engineering', 'Department of Engineering'),
('BIO', 'Biology', 'Department of Biology and Life Sciences'),
('ECON', 'Economics', 'Department of Economics and Business'),
('HIST', 'History', 'Department of History and Social Sciences'),
('ENG-LIT', 'English Literature', 'Department of English and Literature'),
('PSY', 'Psychology', 'Department of Psychology and Behavioral Sciences');

-- Insert sample professors
INSERT INTO professors (first_name, last_name, title, bio, department_id) VALUES
-- Computer Science Department
('John', 'Smith', 'Dr.', 'Professor of Computer Science specializing in algorithms and data structures', 1),
('Sarah', 'Johnson', 'Dr.', 'Associate Professor focusing on machine learning and artificial intelligence', 1),
('Michael', 'Brown', 'Prof.', 'Senior Professor in software engineering and system design', 1),
('Emily', 'Davis', 'Dr.', 'Assistant Professor researching database systems and big data', 1),

-- Mathematics Department
('Robert', 'Wilson', 'Dr.', 'Professor of Pure Mathematics specializing in algebra and number theory', 2),
('Lisa', 'Garcia', 'Dr.', 'Associate Professor in applied mathematics and statistics', 2),
('David', 'Miller', 'Prof.', 'Distinguished Professor of calculus and mathematical analysis', 2),

-- Physics Department
('James', 'Anderson', 'Dr.', 'Professor of Theoretical Physics focusing on quantum mechanics', 3),
('Jennifer', 'Taylor', 'Dr.', 'Associate Professor in experimental physics and optics', 3),
('William', 'Thomas', 'Prof.', 'Senior Professor specializing in astrophysics and cosmology', 3),

-- Chemistry Department
('Christopher', 'Jackson', 'Dr.', 'Professor of Organic Chemistry and pharmaceutical research', 4),
('Michelle', 'White', 'Dr.', 'Associate Professor in physical chemistry and materials science', 4),

-- Engineering Department
('Daniel', 'Harris', 'Dr.', 'Professor of Mechanical Engineering and robotics', 5),
('Ashley', 'Martin', 'Dr.', 'Associate Professor in electrical engineering and signal processing', 5),

-- Biology Department
('Matthew', 'Thompson', 'Dr.', 'Professor of Molecular Biology and genetics', 6),
('Jessica', 'Moore', 'Dr.', 'Associate Professor in ecology and environmental biology', 6);

-- Insert sample courses
INSERT INTO courses (code, name, description, credits, department_id) VALUES
-- Computer Science Courses
('CS101', 'Introduction to Programming', 'Basic programming concepts using Python and Java', 3, 1),
('CS201', 'Data Structures and Algorithms', 'Fundamental data structures and algorithm analysis', 4, 1),
('CS301', 'Database Systems', 'Relational databases, SQL, and database design principles', 3, 1),
('CS401', 'Machine Learning', 'Introduction to machine learning algorithms and applications', 4, 1),
('CS202', 'Object-Oriented Programming', 'Advanced programming using object-oriented principles', 3, 1),
('CS302', 'Software Engineering', 'Software development lifecycle and project management', 3, 1),

-- Mathematics Courses
('MATH101', 'Calculus I', 'Differential calculus and applications', 4, 2),
('MATH201', 'Calculus II', 'Integral calculus and infinite series', 4, 2),
('MATH301', 'Linear Algebra', 'Vector spaces, matrices, and linear transformations', 3, 2),
('MATH401', 'Abstract Algebra', 'Groups, rings, and fields in abstract mathematics', 4, 2),
('MATH202', 'Statistics', 'Probability theory and statistical analysis', 3, 2),

-- Physics Courses
('PHYS101', 'General Physics I', 'Mechanics, waves, and thermodynamics', 4, 3),
('PHYS201', 'General Physics II', 'Electricity, magnetism, and optics', 4, 3),
('PHYS301', 'Quantum Mechanics', 'Introduction to quantum theory and applications', 4, 3),
('PHYS401', 'Advanced Physics', 'Modern physics and research topics', 4, 3),

-- Chemistry Courses
('CHEM101', 'General Chemistry I', 'Atomic structure, bonding, and stoichiometry', 4, 4),
('CHEM201', 'Organic Chemistry I', 'Structure and reactions of organic compounds', 4, 4),
('CHEM301', 'Physical Chemistry', 'Thermodynamics and kinetics of chemical systems', 4, 4),

-- Engineering Courses
('ENG101', 'Engineering Fundamentals', 'Introduction to engineering principles and design', 3, 5),
('ENG201', 'Mechanics of Materials', 'Stress, strain, and material properties', 4, 5),
('ENG301', 'Robotics and Automation', 'Design and control of robotic systems', 4, 5),

-- Biology Courses
('BIO101', 'General Biology I', 'Cell biology, genetics, and molecular biology', 4, 6),
('BIO201', 'General Biology II', 'Ecology, evolution, and biodiversity', 4, 6),
('BIO301', 'Molecular Biology', 'Advanced topics in molecular and cellular biology', 4, 6);

-- Insert course-professor relationships
INSERT INTO course_professors (course_id, professor_id) VALUES
-- CS Department assignments
(1, 1), (1, 3),  -- CS101: John Smith, Michael Brown
(2, 1), (2, 4),  -- CS201: John Smith, Emily Davis
(3, 4),          -- CS301: Emily Davis
(4, 2),          -- CS401: Sarah Johnson
(5, 1), (5, 3),  -- CS202: John Smith, Michael Brown
(6, 3),          -- CS302: Michael Brown

-- Math Department assignments
(7, 5), (7, 7),   -- MATH101: Robert Wilson, David Miller
(8, 5), (8, 7),   -- MATH201: Robert Wilson, David Miller
(9, 6),           -- MATH301: Lisa Garcia
(10, 5),          -- MATH401: Robert Wilson
(11, 6),          -- MATH202: Lisa Garcia

-- Physics Department assignments
(12, 8), (12, 9), -- PHYS101: James Anderson, Jennifer Taylor
(13, 8), (13, 9), -- PHYS201: James Anderson, Jennifer Taylor
(14, 8),          -- PHYS301: James Anderson
(15, 10),         -- PHYS401: William Thomas

-- Chemistry Department assignments
(16, 11), (16, 12), -- CHEM101: Christopher Jackson, Michelle White
(17, 11),           -- CHEM201: Christopher Jackson
(18, 12),           -- CHEM301: Michelle White

-- Engineering Department assignments
(19, 13), (19, 14), -- ENG101: Daniel Harris, Ashley Martin
(20, 13),           -- ENG201: Daniel Harris
(21, 13),           -- ENG301: Daniel Harris

-- Biology Department assignments
(22, 15), (22, 16), -- BIO101: Matthew Thompson, Jessica Moore
(23, 16),           -- BIO201: Jessica Moore
(24, 15);           -- BIO301: Matthew Thompson

-- Insert course prerequisites
INSERT INTO course_prerequisites (course_id, prerequisite_code) VALUES
(2, 'CS101'),     -- CS201 requires CS101
(4, 'CS201'),     -- CS401 requires CS201
(4, 'MATH202'),   -- CS401 requires MATH202
(5, 'CS101'),     -- CS202 requires CS101
(6, 'CS202'),     -- CS302 requires CS202
(8, 'MATH101'),   -- MATH201 requires MATH101
(9, 'MATH201'),   -- MATH301 requires MATH201
(10, 'MATH301'),  -- MATH401 requires MATH301
(13, 'PHYS101'),  -- PHYS201 requires PHYS101
(14, 'PHYS201'),  -- PHYS301 requires PHYS201
(14, 'MATH201'),  -- PHYS301 requires MATH201
(15, 'PHYS301'),  -- PHYS401 requires PHYS301
(17, 'CHEM101'),  -- CHEM201 requires CHEM101
(18, 'CHEM201'),  -- CHEM301 requires CHEM201
(18, 'MATH201'),  -- CHEM301 requires MATH201
(20, 'ENG101'),   -- ENG201 requires ENG101
(21, 'ENG201'),   -- ENG301 requires ENG201
(23, 'BIO101'),   -- BIO201 requires BIO101
(24, 'BIO101');   -- BIO301 requires BIO101

-- Insert sample users (students)
INSERT INTO users (first_name, last_name, email, student_id, password, major, graduation_year, role, email_verified) VALUES
('Alice', 'Cooper', 'alice.cooper@university.edu', 'S2021001', '$2a$10$example.hash.password.alice', 'Computer Science', 2025, 'STUDENT', true),
('Bob', 'Johnson', 'bob.johnson@university.edu', 'S2021002', '$2a$10$example.hash.password.bob', 'Mathematics', 2024, 'STUDENT', true),
('Carol', 'Williams', 'carol.williams@university.edu', 'S2022001', '$2a$10$example.hash.password.carol', 'Physics', 2026, 'STUDENT', true),
('David', 'Brown', 'david.brown@university.edu', 'S2022002', '$2a$10$example.hash.password.david', 'Engineering', 2025, 'STUDENT', true),
('Emma', 'Davis', 'emma.davis@university.edu', 'S2021003', '$2a$10$example.hash.password.emma', 'Chemistry', 2024, 'STUDENT', true),
('Frank', 'Miller', 'frank.miller@university.edu', 'S2023001', '$2a$10$example.hash.password.frank', 'Biology', 2027, 'STUDENT', true),
('Grace', 'Wilson', 'grace.wilson@university.edu', 'S2022003', '$2a$10$example.hash.password.grace', 'Computer Science', 2025, 'STUDENT', true),
('Henry', 'Taylor', 'henry.taylor@university.edu', 'S2021004', '$2a$10$example.hash.password.henry', 'Mathematics', 2024, 'STUDENT', true);

-- Insert sample ratings
INSERT INTO ratings (overall_rating, teaching_quality, difficulty, workload, clarity, review_text, would_take_again, is_anonymous, semester, year, user_id, professor_id, course_id) VALUES
-- Ratings for CS101 with John Smith
(4.5, 4.8, 3.2, 3.5, 4.6, 'Professor Smith explains concepts very clearly and is always available for help. The course is well-structured and engaging.', true, false, 'Fall', 2023, 1, 1, 1),
(4.0, 4.2, 3.8, 4.0, 4.1, 'Good introduction to programming. Assignments are challenging but fair. Would recommend for beginners.', true, false, 'Spring', 2024, 7, 1, 1),

-- Ratings for CS201 with John Smith
(4.7, 4.9, 4.5, 4.2, 4.8, 'Excellent course on data structures. Professor Smith makes complex topics understandable. Highly recommended!', true, false, 'Fall', 2023, 1, 1, 2),
(3.8, 4.0, 4.8, 4.5, 3.5, 'Very challenging course but you learn a lot. Be prepared to spend significant time on assignments.', true, true, 'Spring', 2024, 7, 1, 2),

-- Ratings for CS401 with Sarah Johnson
(4.3, 4.5, 4.0, 3.8, 4.2, 'Great introduction to machine learning. Professor Johnson is knowledgeable and provides real-world examples.', true, false, 'Fall', 2023, 1, 2, 4),
(4.6, 4.8, 3.5, 3.2, 4.7, 'Amazing course! Professor Johnson makes ML concepts accessible and the projects are interesting.', true, false, 'Spring', 2024, 7, 2, 4),

-- Ratings for MATH101 with Robert Wilson
(3.9, 4.1, 4.2, 4.3, 3.8, 'Solid calculus course. Professor Wilson knows the material well but can be a bit dry in lectures.', true, false, 'Fall', 2023, 2, 5, 7),
(4.2, 4.4, 4.0, 3.9, 4.3, 'Good foundation in calculus. Professor Wilson is helpful during office hours.', true, false, 'Spring', 2024, 8, 5, 7),

-- Ratings for PHYS101 with James Anderson
(4.4, 4.6, 3.8, 3.5, 4.5, 'Professor Anderson makes physics interesting and relatable. Great use of demonstrations in class.', true, false, 'Fall', 2023, 3, 8, 12),
(4.1, 4.3, 4.1, 3.8, 4.0, 'Solid physics course. The lab component is well-integrated with lectures.', true, false, 'Spring', 2024, 3, 8, 12),

-- Ratings for CHEM101 with Christopher Jackson
(3.7, 3.9, 4.5, 4.2, 3.5, 'Challenging course with a lot of material to cover. Professor Jackson is knowledgeable but moves quickly.', false, true, 'Fall', 2023, 5, 11, 16),
(4.0, 4.2, 4.3, 4.0, 3.8, 'Comprehensive chemistry course. Be prepared for a heavy workload but you will learn a lot.', true, false, 'Spring', 2024, 5, 11, 16),

-- Ratings for BIO101 with Matthew Thompson
(4.5, 4.7, 3.2, 3.0, 4.6, 'Excellent introduction to biology. Professor Thompson is passionate about the subject and it shows.', true, false, 'Fall', 2023, 6, 15, 22),
(4.3, 4.5, 3.5, 3.3, 4.4, 'Great course for biology majors. The labs are particularly well-designed and informative.', true, false, 'Spring', 2024, 6, 15, 22),

-- Ratings for ENG101 with Daniel Harris
(4.1, 4.3, 3.6, 3.8, 4.0, 'Good introduction to engineering principles. Professor Harris brings industry experience to the classroom.', true, false, 'Fall', 2023, 4, 13, 19),
(3.9, 4.1, 3.9, 4.1, 3.7, 'Practical course with real-world applications. Some lectures could be more engaging.', true, false, 'Spring', 2024, 4, 13, 19);