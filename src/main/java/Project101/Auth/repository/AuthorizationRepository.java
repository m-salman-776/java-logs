package Project101.Auth.repository;

import Project101.Auth.model.Permission;
import Project101.Auth.model.PermissionGrant;
import Project101.Auth.model.Role;
import Project101.Auth.model.Scope;
import Project101.Auth.policy.Policy;

import java.util.List;
import java.util.Set;

public interface AuthorizationRepository {
    Set<Role> findRolesForUser(int userId);

    /**
     * Finds all permission grants associated with a given set of roles.
     * This is the primary method for fetching permissions in Scoped RBAC.
     */
    Set<PermissionGrant> findGrantsForRoles(Set<Role> roles);

    /**
     * Finds all policies that govern a specific permission.
     */
    List<Policy> findPoliciesForPermission(Permission permission);

    void addGrant(Role role, Permission permission, Scope scope);
    void removeGrant(Role role, Permission permission, Scope scope);
    void addPermissionPolicy(Permission permission, Policy policy);
    void addUserRole(int userId, Role role);
}
