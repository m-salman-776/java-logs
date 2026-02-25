package Project101.Auth.repository;

import Project101.Auth.model.*;
import Project101.Auth.policy.Policy;

import java.util.*;

public class MockAuthorizationRepository implements AuthorizationRepository {
    // userId : list of Roles
    private final Map<Integer, Set<Role>> userRoles = new HashMap<>();
    
    // RoleId -> (PermissionName -> List<Scope>)
    private final Map<String, Map<String, List<Scope>>> grants = new HashMap<>();
    
    private final Map<String, List<Policy>> permissionPolicies = new HashMap<>();

    @Override
    public void addUserRole(int userId, Role role) {
        userRoles.computeIfAbsent(userId, k -> new HashSet<>()).add(role);
    }

    @Override
    public void addGrant(Role role, Permission permission, Scope scope) {
        grants.computeIfAbsent(role.getId(), k -> new HashMap<>())
              .computeIfAbsent(permission.getName(), k -> new ArrayList<>())
              .add(scope);
    }

    @Override
    public void removeGrant(Role role, Permission permission, Scope scope) {
        Map<String, List<Scope>> roleGrants = grants.get(role.getId());
        if (roleGrants != null) {
            List<Scope> scopes = roleGrants.get(permission.getName());
            if (scopes != null) {
                scopes.remove(scope);
                if (scopes.isEmpty()) {
                    roleGrants.remove(permission.getName());
                }
            }
        }
    }

    @Override
    public void addPermissionPolicy(Permission permission, Policy policy) {
        permissionPolicies.computeIfAbsent(permission.getName(), k -> new ArrayList<>()).add(policy);
    }

    @Override
    public Set<Role> findRolesForUser(int userId) {
        return userRoles.getOrDefault(userId, Collections.emptySet());
    }

    @Override
    public Set<PermissionGrant> findGrantsForRoles(Set<Role> roles) {
        Set<PermissionGrant> result = new HashSet<>();
        
        for (Role role : roles) {
            Map<String, List<Scope>> roleGrants = grants.get(role.getId());
            if (roleGrants != null) {
                for (Map.Entry<String, List<Scope>> entry : roleGrants.entrySet()) {
                    String permissionName = entry.getKey();
                    List<Scope> scopes = entry.getValue();
                    for (Scope scope : scopes) {
                        result.add(new PermissionGrant(role, new Permission(permissionName), scope));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<Policy> findPoliciesForPermission(Permission permission) {
        return permissionPolicies.getOrDefault(permission.getName(), Collections.emptyList());
    }
}
