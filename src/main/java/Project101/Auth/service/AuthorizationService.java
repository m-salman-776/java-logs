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

    public boolean isAuthorized(User user, AuthorizableResource resource, String permissionName) {
        System.out.println("\n--- Checking auth for user '" + user.getName() + "' to '" + permissionName + "' on " + resource.getResourceType() + " '" + resource.getResourceId() + "' ---");

        // 1. Get all permission grants for the user's roles.
        Set<Role> userRoles = authRepository.findRolesForUser(user);
        if (userRoles.isEmpty()) {
            System.out.println("Result: DENIED. Reason: User has no roles.");
            return false;
        }
        Set<PermissionGrant> grants = authRepository.findGrantsForRoles(userRoles);

        // 2. Start the recursive check, beginning with the resource itself.
        return checkAccessRecursive(user, resource, new Permission(permissionName), grants);
    }

    private boolean checkAccessRecursive(User user, AuthorizableResource resource, Permission permission, Set<PermissionGrant> grants) {
        // Find grants that match the current resource and permission.
        // We check in order of specificity: INSTANCE -> TYPE -> GLOBAL
        
        // Step 2a: Check for a grant scoped to this specific resource instance.
        if (hasPermission(user, resource, permission, grants, Scope.ofInstance(resource.getResourceId()))) {
            System.out.println("Result: GRANTED. Reason: User has a permission grant on the specific resource instance '" + resource.getResourceId() + "'.");
            return true;
        }

        // Step 2b: Check for a grant scoped to this resource's type.
        if (hasPermission(user, resource, permission, grants, Scope.ofType(resource.getResourceType()))) {
            System.out.println("Result: GRANTED. Reason: User has a permission grant for the resource type '" + resource.getResourceType() + "'.");
            return true;
        }

        // Step 2c: Check for a global grant.
        if (hasPermission(user, resource, permission, grants, Scope.global())) {
            System.out.println("Result: GRANTED. Reason: User has a global permission grant for '" + permission.getName() + "'.");
            return true;
        }

        // Step 3: If no permission found at this level, traverse up the hierarchy.
        String parentId = resource.getParentId();
        if (parentId != null) {
            System.out.println("No permission found at this level. Checking parent '" + parentId + "' for inherited permissions...");
            AuthorizableResource parentResource = resourceRepository.findById(parentId);
            if (parentResource != null) {
                return checkAccessRecursive(user, parentResource, permission, grants);
            }
        }

        System.out.println("Result: DENIED. Reason: No applicable permission grant found at this level or any parent level.");
        return false;
    }

    private boolean hasPermission(User user, AuthorizableResource resource, Permission permission, Set<PermissionGrant> grants, Scope scope) {
        // Filter grants to find one that matches the desired permission and scope.
//        boolean rbacPassed = grants.stream()
//                .anyMatch(grant -> grant.getPermission().equals(permission) && grant.getScope().equals(scope));
//
        boolean rbacPassed = false;
        for (PermissionGrant grant : grants) {
            if (grant.getPermission().equals(permission) && grant.getScope().equals(scope)) {
                rbacPassed = true;
                break;
            }
        }

        if (!rbacPassed) {
            return false;
        }

        // If RBAC passes for this scope, we must also check the associated ABAC policies.
        List<Policy> policies = authRepository.findPoliciesForPermission(permission);
        for (Policy policy : policies) {
            if (!policy.evaluate(user, resource, permission)) {
                System.out.println("  - ABAC Policy Failed: " + policy.getClass().getSimpleName() + " for scope " + scope.getType() + ":" + scope.getValue());
                return false;
            }
        }
        return true;
    }
}
