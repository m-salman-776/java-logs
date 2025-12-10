package Project101.Auth.model;

import lombok.Getter;

/**
 * Represents a single, explicit grant of a permission to a role within a specific scope.
 * This is the core of the Scoped RBAC model.
 */
@Getter
public class PermissionGrant {
    private final Role role;
    private final Permission permission;
    private final Scope scope;

    public PermissionGrant(Role role, Permission permission, Scope scope) {
        this.role = role;
        this.permission = permission;
        this.scope = scope;
    }

}
