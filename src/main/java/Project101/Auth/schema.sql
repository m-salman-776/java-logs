-- 1. Roles Table
CREATE TABLE roles (
    role_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);

-- 2. Permissions Table
CREATE TABLE permissions (
    permission_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);

-- 3. Users Table
CREATE TABLE users (
    user_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- 4. User-Roles Join Table (Many-to-Many)
CREATE TABLE user_roles (
    user_id VARCHAR(255) NOT NULL,
    role_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

-- 5. Permission Grants Table (The core of Scoped RBAC)
CREATE TABLE permission_grants (
    grant_id INT AUTO_INCREMENT PRIMARY KEY,
    role_id VARCHAR(255) NOT NULL,
    permission_id VARCHAR(255) NOT NULL,
    scope_type ENUM('GLOBAL', 'RESOURCE_TYPE', 'RESOURCE_INSTANCE') NOT NULL,
    scope_value VARCHAR(255) NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(permission_id) ON DELETE CASCADE,
    UNIQUE (role_id, permission_id, scope_type, scope_value)
);

-- 6. Permission Policies Table (For ABAC)
CREATE TABLE permission_policies (
    policy_id INT AUTO_INCREMENT PRIMARY KEY,
    permission_id VARCHAR(255) NOT NULL,
    policy_class_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (permission_id) REFERENCES permissions(permission_id) ON DELETE CASCADE
);