package RBAC.Classes;

import java.util.HashSet;
import java.util.Set;

class RBACManager {
    private Set<Role> roles;
    private Set<Permission> permissions;

    public RBACManager() {
        this.roles = new HashSet<>();
        this.permissions = new HashSet<>();
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    public void addPermission(Permission permission) {
        permissions.add(permission);
    }

    public void removePermission(Permission permission) {
        permissions.remove(permission);
    }

    public boolean hasAccess(User user, Resource resource, String action) {
        // Check if the user has permission for the specified action on the resource
        for (Role role : user.getRoles()) {
            for (Permission permission : role.getPermissions()) {
                if (permission.getName().equals(action) && permission.isApplicableTo(resource)) {
                    return true;
                }
            }
        }
        return false;
    }
}
