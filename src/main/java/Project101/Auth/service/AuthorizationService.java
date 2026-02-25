package Project101.Auth.service;

import Project101.Auth.model.*;
import Project101.Auth.policy.Policy;
import Project101.Auth.repository.AuthorizationRepository;
import Project101.Auth.repository.ResourceRepository;
import Project101.Auth.resource.AuthorizableResource;

import java.util.List;
import java.util.Set;

public class AuthorizationService {
    private final AuthorizationRepository authRepository;
    private final ResourceRepository resourceRepository;

    public AuthorizationService(AuthorizationRepository authRepository, ResourceRepository resourceRepository) {
        this.authRepository = authRepository;
        this.resourceRepository = resourceRepository;
    }

    public boolean isAuthorized(int userId, AuthorizableResource resource, String permissionName) {
        System.out.println("\n--- Checking auth for user '" + userId + "' to '" + permissionName + "' on " + resource.getResourceType() + " '" + resource.getResourceId() + "' ---");

        Set<Role> userRoles = authRepository.findRolesForUser(userId);
        if (userRoles.isEmpty()) {
            System.out.println("Result: DENIED. Reason: User has no roles.");
            return false;
        }
        Set<PermissionGrant> grants = authRepository.findGrantsForRoles(userRoles);

        return checkAccessRecursive(userId, resource, new Permission(permissionName), grants);
    }

    public void grantAccess(Role role, Permission permission, Scope scope) {
        authRepository.addGrant(role, permission, scope);
    }

    public void revokeAccess(Role role, Permission permission, Scope scope) {
        authRepository.removeGrant(role, permission, scope);
    }

    public void assignRole(int userId, Role role) {
        authRepository.addUserRole(userId, role);
    }

    public void addPolicy(Permission permission, Policy policy) {
        authRepository.addPermissionPolicy(permission, policy);
    }

    private boolean checkAccessRecursive(int userId, AuthorizableResource resource, Permission permission, Set<PermissionGrant> grants) {
        if (checkGrant(userId, resource, permission, grants, Scope.ofInstance(resource.getResourceId()), 
                "permission grant on the specific resource instance '" + resource.getResourceId() + "'")) return true;

        if (checkGrant(userId, resource, permission, grants, Scope.ofType(resource.getResourceType()), 
                "permission grant for the resource type '" + resource.getResourceType() + "'")) return true;

        if (checkGrant(userId, resource, permission, grants, Scope.global(), 
                "global permission grant for '" + permission.getName() + "'")) return true;

        String parentId = resource.getParentId();
        if (parentId != null) {
            System.out.println("No permission found at this level. Checking parent '" + parentId + "' for inherited permissions...");
            AuthorizableResource parentResource = resourceRepository.findById(parentId);
            if (parentResource != null) {
                return checkAccessRecursive(userId, parentResource, permission, grants);
            }
        }

        System.out.println("Result: DENIED. Reason: No applicable permission grant found at this level or any parent level.");
        return false;
    }

    private boolean checkGrant(int userId, AuthorizableResource resource, Permission permission, Set<PermissionGrant> grants, Scope scope, String reason) {
        if (hasPermission(userId, resource, permission, grants, scope)) {
            System.out.println("Result: GRANTED. Reason: User has a " + reason + ".");
            return true;
        }
        return false;
    }

    private boolean hasPermission(int userId, AuthorizableResource resource, Permission permission, Set<PermissionGrant> grants, Scope scope) {
        boolean rbacPassed = false;
        for (PermissionGrant grant : grants) {
            if (grant.getPermission().equals(permission) && grant.getScope().equals(scope)) {
                rbacPassed = true;
                break;
            }
        }

        if (!rbacPassed) return false;

        List<Policy> policies = authRepository.findPoliciesForPermission(permission);
        for (Policy policy : policies) {
            if (!policy.evaluate(userId, resource)) {
                System.out.println("  - ABAC Policy Failed: " + policy.getClass().getSimpleName() + " for scope " + scope.getType() + ":" + scope.getValue());
                return false;
            }
        }
        return true;
    }
}
