package Project101.Auth.repository;

import Project101.Auth.model.*;
import Project101.Auth.policy.Policy;

import java.util.*;

public class MockAuthorizationRepository implements AuthorizationRepository {
    private final Map<String, Set<Role>> userRoles = new HashMap<>();
    private final Set<PermissionGrant> grants = new HashSet<>();
    private final Map<String, List<Policy>> permissionPolicies = new HashMap<>();

    public void addUserRole(User user, Role role) {
        userRoles.computeIfAbsent(user.getId(), k -> new HashSet<>()).add(role);
    }

    public void addGrant(Role role, Permission permission, Scope scope) {
        grants.add(new PermissionGrant(role, permission, scope));
    }

    public void addPermissionPolicy(Permission permission, Policy policy) {
        permissionPolicies.computeIfAbsent(permission.getName(), k -> new ArrayList<>()).add(policy);
    }
    public List<Policy> getPermissionPolicy(Permission permission){
        return permissionPolicies.getOrDefault(permission.getName(),new ArrayList<>());
    }

    @Override
    public Set<Role> findRolesForUser(User user) {
        return userRoles.getOrDefault(user.getId(), Collections.emptySet());
    }

    @Override
    public Set<PermissionGrant> findGrantsForRoles(Set<Role> roles) {
        Set<PermissionGrant> results = new HashSet<>();
        Set<String> roleIds = new HashSet<>();
        for (Role role : roles){
            roleIds.add(role.getId());
        }
        for (PermissionGrant grant : grants) {
            if (roleIds.contains(grant.getRole().getId())) {
                results.add(grant);
            }
        }
        return results;
    }

    @Override
    public List<Policy> findPoliciesForPermission(Permission permission) {
        return permissionPolicies.getOrDefault(permission.getName(), Collections.emptyList());
    }
}

