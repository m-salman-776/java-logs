-- Table for User
CREATE TABLE Users (
    UserId INT PRIMARY KEY,
    Username VARCHAR(255) NOT NULL
);

-- Table for Role
CREATE TABLE Roles (
    RoleId INT PRIMARY KEY,
    RoleName VARCHAR(255) NOT NULL
);

-- Table for Permission
CREATE TABLE Permissions (
    PermissionId INT PRIMARY KEY,
    PermissionName VARCHAR(255) NOT NULL,
    ResourceId INT, -- Foreign key referencing Resources table
    FOREIGN KEY (ResourceId) REFERENCES Resources(ResourceId)
);

-- Table for Resource
CREATE TABLE Resources (
    ResourceId INT PRIMARY KEY,
    ResourceName VARCHAR(255) NOT NULL,
    ResourceType VARCHAR(50) NOT NULL, -- 'Folder' or 'File'
    ParentId INT
    FOREIGN KEY (ResourceId) REFERENCES Resources(ResourceId)
);

-- Table for Folder
--CREATE TABLE Folders (
--    FolderId INT PRIMARY KEY,
--    FolderName VARCHAR(255) NOT NULL
--);
--
---- Table for File
--CREATE TABLE Files (
--    FileId INT PRIMARY KEY,
--    FileName VARCHAR(255) NOT NULL
--);

-- Table for UserRole relationship (many-to-many)
CREATE TABLE UserRoles (
    UserId INT,
    RoleId INT,
    PRIMARY KEY (UserId, RoleId),
    FOREIGN KEY (UserId) REFERENCES Users(UserId),
    FOREIGN KEY (RoleId) REFERENCES Roles(RoleId)
);

-- Table for RolePermission relationship (many-to-many)
CREATE TABLE RolePermissions (
    RoleId INT,
    PermissionId INT,
    PRIMARY KEY (RoleId, PermissionId),
    FOREIGN KEY (RoleId) REFERENCES Roles(RoleId),
    FOREIGN KEY (PermissionId) REFERENCES Permissions(PermissionId)
);

-- Table for PermissionResource relationship (many-to-one, polymorphic)
CREATE TABLE PermissionResources (
    PermissionId INT,
    ResourceId INT,
    PRIMARY KEY (PermissionId, ResourceId),
    FOREIGN KEY (PermissionId) REFERENCES Permissions(PermissionId),
    FOREIGN KEY (ResourceId) REFERENCES Resources(ResourceId)
);
