package RBAC;

import RBAC.Classes.Resource.*;

import RBAC.Classes.Resource;


public class RBACExample {
    public static void main(String[] args) {
        // Create resources
//        Folder rootFolder = new Folder("Root");
//        Folder subFolder = new Folder("Subfolder");
//        File file = new File("File.txt");
//
//        // Add resources to the hierarchy
//        rootFolder.addResource(subFolder);
//        subFolder.addResource(file);
//
//        // Create permissions
//        Permission readRootPermission = new Permission("READ", rootFolder);
//        Permission writeSubFolderPermission = new Permission("WRITE", subFolder);
//
//        // Create roles
//        Role adminRole = new Role("ADMIN");
//
//        // Assign permissions to roles
//        adminRole.addPermission(readRootPermission);
//        adminRole.addPermission(writeSubFolderPermission);
//
//        // Create users
//        User adminUser = new User("admin");
//
//        // Assign roles to users
//        adminUser.addRole(adminRole);
//
//        // Create RBAC manager
//        RBAC rbacManager = new RBACManager();
//
//        // Add roles and permissions to the RBAC manager
//        rbacManager.addRole(adminRole);
//        rbacManager.addPermission(readRootPermission);
//        rbacManager.addPermission(writeSubFolderPermission);
//
//        // Check access for users
//        System.out.println("Admin has READ access to Root: " + rbacManager.hasAccess(adminUser, rootFolder, "READ"));
//        System.out.println("Admin has WRITE access to Subfolder: " + rbacManager.hasAccess(adminUser, subFolder, "WRITE"));
//        System.out.println("Admin has READ access to File: " + rbacManager.hasAccess(adminUser, file, "READ"));
    }
}

