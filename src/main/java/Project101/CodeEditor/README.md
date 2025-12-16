-- Stores the code submitted by the user
CREATE TABLE submissions (
id VARCHAR(255) PRIMARY KEY,
user_id VARCHAR(255) NOT NULL,
code TEXT NOT NULL,
language ENUM('JAVA', 'PYTHON', 'CPP') NOT NULL,
status ENUM('QUEUED', 'PROCESSING', 'SUCCESS', 'RUNTIME_ERROR', 'COMPILATION_ERROR') NOT NULL,
submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Stores user information
CREATE TABLE users (
id VARCHAR(255) PRIMARY KEY,
username VARCHAR(255) NOT NULL UNIQUE,
email VARCHAR(255) NOT NULL UNIQUE,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Stores the output of the execution
CREATE TABLE execution_results (
submission_id VARCHAR(255) PRIMARY KEY,
stdout TEXT,
stderr TEXT,
execution_time_ms LONG,
memory_used_kb LONG,
exit_code INT,
FOREIGN KEY (submission_id) REFERENCES submissions(id)
);